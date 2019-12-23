package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.OpCodeAscii;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.shuffle.ShuffleTechniques;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 22
 * https://adventofcode.com/2019/day/22
 */
public class Day22 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        int[] result = ShuffleTechniques.shuffle(InputUtil.readInputAsList(22, true), 10007);
        for(int i=0; i<result.length; i++) {
            if(result[i] == 2019) {
                return i;
            }
        }
        return -1;
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
            Log.i(String.format("Position of card 2019: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
