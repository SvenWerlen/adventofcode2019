package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.OpCodeAscii;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.shuffle.Shuffle;
import org.boisdechet.adventofcode2019.shuffle.ShuffleTechniques;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * (I cheated for that one because I was not able to understand the math behind it. See Shuffle class.)
     */
    public static long part2() throws IOException {
        Shuffle shuffle = Shuffle.get(119315717514047L, InputUtil.readInputAsList(22, true).toArray(new String[0]));
        Shuffle repeatedShuffle = shuffle.repeat(101741582076661L);
        return repeatedShuffle.invert().apply(2020);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Position of card 2019: %d", part1()));
            Log.i(String.format("Position of card 2020: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
