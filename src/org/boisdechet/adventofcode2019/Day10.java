package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.AsteroidsMap;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 10
 * https://adventofcode.com/2019/day/10
 */
public class Day10 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        AsteroidsMap.BestLocation location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputAsString(10, true),'#')).getBestLocation();
        Log.i(String.format("Best found location is %s", location.point));
        return location.visibleAsteroids;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        return 0L;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of asteroids that can be detected: %d", part1()));
            //Log.i(String.format("Coordinates of the distress signal: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
