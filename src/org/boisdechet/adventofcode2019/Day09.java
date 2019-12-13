package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.image.SpaceImage;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.Map;

/**
 * Solver for 2019 Day 9
 * https://adventofcode.com/2019/day/9
 */
public class Day09 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        OpCodeMachine machine = new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(9, true)));
        long code = machine.execute(1);
        long lastValid = 0;
        while(code != OpCodeMachine.HALT) {
            lastValid = code;
            code = machine.execute(1,1, false);
        }
        return lastValid;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        OpCodeMachine machine = new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(9, true)));
        long code = machine.execute(2);
        long lastValid = 0;
        while(code != OpCodeMachine.HALT) {
            lastValid = code;
            code = machine.execute(2, 2, false);
        }
        return lastValid;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("BOOST keycode: %d", part1()));
            Log.i(String.format("Coordinates of the distress signal: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
