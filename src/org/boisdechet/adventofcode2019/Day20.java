package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Beam;
import org.boisdechet.adventofcode2019.coord.DonutMaze;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 20
 * https://adventofcode.com/2019/day/20
 */
public class Day20 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        DonutMaze dm = new DonutMaze(InputUtil.readInputAsString(20, true));
        return dm.getShortestPath().getDistance();
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
            Log.i(String.format("Steps from AA to ZZ: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
