package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.bugs.Eris;
import org.boisdechet.adventofcode2019.computer.Network;
import org.boisdechet.adventofcode2019.coord.PointL;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 24
 * https://adventofcode.com/2019/day/24
 */
public class Day24 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        Eris e = new Eris(InputUtil.readInputAsString(24, true));
        e.untilFirstMatch();
        return e.getBiodiversityRating();
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
            Log.i(String.format("Biodiversity rating for the first layout that appears twice: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
