package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.bugs.Eris;
import org.boisdechet.adventofcode2019.droid.Droid;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Solver for 2019 Day 25
 * https://adventofcode.com/2019/day/25
 */
public class Day25 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        Droid d = new Droid(InputUtil.convertToLongArray(InputUtil.readInputAsString(25, true)));
        //return d.findCombination(false);
        return d.findCombination(true);
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
            //new Droid(InputUtil.convertToLongArray(InputUtil.readInputAsString(25, true))).runUserInteractive();
            Log.i(String.format("Password for the main airlock: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
