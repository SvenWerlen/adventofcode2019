package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.Amplifiers;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 7
 * https://adventofcode.com/2019/day/7
 */
public class Day07 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        String instructions = InputUtil.readInputAsString(7, true);
        return Amplifiers.getMaxThrustersOutput(InputUtil.convertToIntArray("0,1,2,3,4"), InputUtil.convertToIntArray(instructions));
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        String instructions = InputUtil.readInputAsString(7, true);
        return Amplifiers.getMaxThrustersOutput(InputUtil.convertToIntArray("5,6,7,8,9"), InputUtil.convertToIntArray(instructions), true);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Highest signal: %d", part1()));
            Log.i(String.format("Highest signal (loop): %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
    }
}
