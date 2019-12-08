package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.boisdechet.adventofcode2019.utils.Permutations;

import java.util.Arrays;
import java.util.List;

public class Amplifiers {

    private int[] sequence;
    private int[] instructions;
    private boolean loop;

    public Amplifiers(int[] sequence, int[] instructions) {
        this(sequence, instructions, false);
    }

    public Amplifiers(int[] sequence, int[] instructions, boolean loop) {
        this.sequence = sequence;
        this.instructions = instructions;
        this.loop = loop;
    }

    public int getThrustersOutput() {
        int output = 0;
        int maxOutput = 0;
        int count = 1;

        // build amplifiers
        OpCodeMachine[] amplifiers = new OpCodeMachine[sequence.length];
        for(int idx=0; idx<sequence.length; idx++) {
            amplifiers[idx] = new OpCodeMachine(instructions);
        }
        Log.d(String.format("Sequence: %s", InputUtil.convertToString(sequence)));
        while(true) {
            for (int idx=0; idx<sequence.length; idx++) {
                output = amplifiers[idx].execute(count == 1 ? sequence[idx] : output, output, false);
                Log.d(String.format("Output (amp #%d) = %d", idx, output));
            }
            Log.d(String.format("Output (loop %d) = %d", count, output));
            if(!loop) {
                break;
            }
            if(output == 0) {
                return maxOutput;
            }
            maxOutput = output;
            count++;
        }
        return output;
    }

    public static int getMaxThrustersOutput(int[] phaseSettings, int[] instructions) {
        return getMaxThrustersOutput(phaseSettings, instructions, false);
    }

    public static int getMaxThrustersOutput(int[] phaseSettings, int[] instructions, boolean feedbackLoop) {
        int outputMax = 0;
        int[] permMax = null;
        List<Integer> input = Arrays.asList (InputUtil.convertToIntegerArray(phaseSettings));
        Permutations<Integer> permutations = new Permutations <Integer> (input);

        for (List<Integer> p : permutations) {
            int[] array = p.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
            Amplifiers amp = new Amplifiers(array, instructions, feedbackLoop);
            int output = amp.getThrustersOutput();
            Log.d(String.format("Output (%s): %d", Arrays.toString(array), output));
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
