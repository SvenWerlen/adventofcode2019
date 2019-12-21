package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.boisdechet.adventofcode2019.utils.Permutations;

import java.util.Arrays;
import java.util.List;

public class Amplifiers {

    private int[] sequence;
    private long[] instructions;
    private boolean loop;

    public Amplifiers(int[] sequence, long[] instructions) {
        this(sequence, instructions, false);
    }

    public Amplifiers(int[] sequence, long[] instructions, boolean loop) {
        this.sequence = sequence;
        this.instructions = instructions;
        this.loop = loop;
    }

    public long getThrustersOutput() {
        long output = 0;
        long maxOutput = 0;
        int count = 1;

        // build amplifiers
        OpCodeMachine[] amplifiers = new OpCodeMachine[sequence.length];
        for(int idx=0; idx<sequence.length; idx++) {
            amplifiers[idx] = new OpCodeMachine(instructions, sequence[idx]);
        }
        if(Log.DEBUG) { Log.d(String.format("Sequence: %s", InputUtil.convertToString(sequence))); }
        while(true) {
            for (int idx=0; idx<sequence.length; idx++) {
                output = amplifiers[idx].execute(Math.toIntExact(output));
                if(Log.DEBUG) { Log.d(String.format("Output (amp #%d) = %d", idx, output)); }
            }
            if(Log.DEBUG) { Log.d(String.format("Output (loop %d) = %d", count, output)); }
            if(!loop) {
                break;
            }
            if(output == OpCodeMachine.HALT) {
                return maxOutput;
            }
            maxOutput = output;
            count++;
        }
        return output;
    }

    public static long getMaxThrustersOutput(int[] phaseSettings, long[] instructions) {
        return getMaxThrustersOutput(phaseSettings, instructions, false);
    }

    public static long getMaxThrustersOutput(int[] phaseSettings, long[] instructions, boolean feedbackLoop) {
        long outputMax = 0;
        int[] permMax = null;
        List<Integer> input = Arrays.asList (InputUtil.convertToIntegerArray(phaseSettings));
        Permutations<Integer> permutations = new Permutations <Integer> (input);

        for (List<Integer> p : permutations) {
            int[] array = p.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
            Amplifiers amp = new Amplifiers(array, instructions, feedbackLoop);
            long output = amp.getThrustersOutput();
            if(Log.DEBUG) { Log.d(String.format("Output (%s): %d", Arrays.toString(array), output)); }
            if (permMax == null || outputMax < output) {
                permMax = array;
                outputMax = output;
            }
        }
        // convert array to number
        int result = 0;
        for(int idx=0; idx<permMax.length; idx++) {
            result += permMax[idx]*Math.pow(10,idx);
        }
        return outputMax;
    }
}
