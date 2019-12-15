package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 5
 * https://adventofcode.com/2019/day/5
 */
public class Day05 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        long[] instructions = InputUtil.convertToLongArray(InputUtil.readInputAsString(5, true));
        OpCodeMachine machine = new OpCodeMachine(instructions);
        long result = 0;
        while(result == 0) {
            result = machine.execute(1,1, false);
        }
        return result;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        long[] instructions = InputUtil.convertToLongArray(InputUtil.readInputAsString(5, true));
        OpCodeMachine machine = new OpCodeMachine(instructions);
        return machine.execute(5);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Diagnostic code: %d", part1()));
            Log.i(String.format("Diagnostic code: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
        Log.bye();
    }
}
