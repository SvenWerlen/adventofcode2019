package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Vault;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 19
 * https://adventofcode.com/2019/day/19
 */
public class Day19 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        int count = 0;
        long[] instr = InputUtil.convertToLongArray(InputUtil.readInputAsString(19, true));
        for(int y = 0; y<50; y++) {
            for(int x = 0; x<50; x++) {
                OpCodeMachine op = new OpCodeMachine(instr, new int[] {x,y});
                if(op.execute(0) == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        return -1;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of points are affected by the tractor beam in the 50x50 area: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
