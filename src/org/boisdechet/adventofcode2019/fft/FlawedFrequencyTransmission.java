package org.boisdechet.adventofcode2019.fft;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FlawedFrequencyTransmission {

    private static final int DIGITS = 8;
    private static final int[] BASE_PATTERN = {0, 1, 0, -1};

    private Map<Integer, Integer[]> patterns;
    private int[] pattern;
    private int[] signal;

    public FlawedFrequencyTransmission(int[] signal) {
        this.patterns = new HashMap<>();
        this.pattern = BASE_PATTERN;
        this.signal = signal;
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
        return result.length() <= DIGITS ? result : result.substring(0, DIGITS);
    }

    /**
     * Only works with given pattern
     */
    public static String cleanSignal(int[] signal, int phases, int repeat, int offset) {
        if(repeat == 0 || offset == 0) {
            return new FlawedFrequencyTransmission(signal).cleanSignal(phases);
        }

        // prepare signal
        int[] megaSignal = new int[signal.length*repeat];
        for(int i=0; i<repeat; i++) { System.arraycopy(signal, 0, megaSignal, i*signal.length, signal.length); }

        int offsetVal = Integer.parseInt(InputUtil.convertToString(signal).substring(0,offset));
        if(offsetVal < megaSignal.length / 2) {
            throw new IllegalStateException(String.format("Signal offset is not large enough! (%d - %d)", offsetVal, megaSignal.length));
        }

        // compute 2nd half for X iterations (digit = sum of last digits, starting from index+1)
        for(int i=0; i<phases; i++) {
            int sum = 0;
            for(int idx=megaSignal.length-1; idx>=offsetVal; idx--) {
                sum += megaSignal[idx];
                megaSignal[idx] = sum % 10;
            }
        }

        // extract output value
        int[] result = new int[DIGITS];
        System.arraycopy(megaSignal, offsetVal, result, 0, DIGITS);

        return InputUtil.convertToString(result);
    }
}
