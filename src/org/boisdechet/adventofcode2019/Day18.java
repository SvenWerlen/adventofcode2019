package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.ScaffoldingMap;
import org.boisdechet.adventofcode2019.coord.Vault;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 18
 * https://adventofcode.com/2019/day/18
 */
public class Day18 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        return new Vault(InputUtil.readInputAsString(18, true)).minStepsToCollectAllKeys();
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
            Log.i(String.format("Steps for the shortest path that collects all of the keys ?: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
