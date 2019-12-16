package org.boisdechet.adventofcode2019.fft;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FlawedFrequencyTransmission {

    private static final int[] BASE_PATTERN = {0, 1, 0, -1};

    private Map<Integer, Integer[]> patterns;
    private int[] pattern;
    private int[] signal;
    private int repeat;
    private int offset;

    public FlawedFrequencyTransmission(int[] signal) {
        this.patterns = new HashMap<>();
        this.pattern = BASE_PATTERN;
        this.signal = signal;
        this.offset = 0;
        this.repeat = 0;
    }

    public FlawedFrequencyTransmission(int[] signal, int repeated, int offset) {
        this(signal);
        this.offset = offset;
        this.repeat = repeated;
    }

    public synchronized  Integer[] getPattern(int idx) {
        if(!patterns.containsKey(idx)) {
            Integer[] patternFinal = new Integer[pattern.length*idx];
            for(int i=0; i<pattern.length; i++) {
                for(int c=0; c<idx; c++) {
                    patternFinal[i*idx+c] = pattern[i];
                }
            }
            patterns.put(idx, patternFinal);
            if(Log.DEBUG) { Log.d(String.format("Pattern for index %d is %s", idx, Arrays.toString(patternFinal))); }
        }
        return patterns.get(idx);
    }

    public int computeSignalSum(int[] input, Integer[] pattern) {
        int total = 0;
        for(int i=0; i<input.length; i++) {
            if(Log.DEBUG) { Log.d(String.format("Sum %d is %d", i, input[i] * pattern[(i+1) % pattern.length])); }
            total += input[i] * pattern[(i+1) % pattern.length];
        }
        return total;
    }

    /**
     * Computes the output digit
     * @param input input signal
     * @param idx index in output (starting from 1)
     * @return cleaned signal output digit
     */
    public int computeSignalDigit(int[] input, int idx) {
        // compute repeating
        Integer[] patternFinal = getPattern(idx);
        // compute addition
        int total = computeSignalSum(input, patternFinal);
        // repeat
        if(repeat > 0) {
            int firstTotal = total;                             // first signal is unique due to 1 bit shift
            int startIdx = input.length % patternFinal.length;  // when startIdx will be identical, extrapolation is possible
            int loop = 1;
            int sigTotal = 0;
            while((loop*input.length % patternFinal.length != startIdx)) {
                sigTotal += computeSignalSum(input, patternFinal);
                loop++;
            }
            // extrapolate
            int extrapoleLoops = (repeat-1)/loop;
            int extrapoleRemains = repeat-1 - extrapoleLoops*loop;
            total = firstTotal + extrapoleLoops * sigTotal;
            Log.d(String.format("Extrapolation possible after %d loops, means %d times and then %d remaining", loop, extrapoleLoops, extrapoleRemains));
            // finalize for the remaining iterations
            for(int l=0; l<extrapoleRemains; l++) {
                total += computeSignalSum(input, patternFinal);
            }

        }
        if(Log.DEBUG) { Log.d(String.format("Sum result for index %d is %s", idx, total)); }
        // extract last digit
        return Math.abs(total % 10);
    }

    public String cleanSignal(int phases) {
        int[] input = signal;
        for(int p=0; p<phases; p++) {
            int[] output = new int[input.length];
            for(int i=0; i<input.length; i++) {
                output[i]=computeSignalDigit(input, i+1);
            }
            input=output;
        }
        String result = InputUtil.convertToString(input);
        if(repeat > 0) {
            StringBuffer buf = new StringBuffer();
            for(int i=0; i<repeat; i++) {
                buf.append(result);
            }
            result = buf.toString();
        }
        if(offset > 0) {
            long offsetVal = Long.parseLong(InputUtil.convertToString(this.signal).substring(0,offset));
            Log.i(String.format("Offset is %d", offsetVal));
            result = result.substring(Math.toIntExact(offsetVal));
        }
        return result.length() <= 8 ? result : result.substring(0, 8);
    }
}
