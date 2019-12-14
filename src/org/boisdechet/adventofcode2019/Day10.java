package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.AsteroidsMap;
import org.boisdechet.adventofcode2019.coord.Point;
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
    public static AsteroidsMap.Location part1() throws IOException {
        AsteroidsMap.Location location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputAsString(10, true),'#')).getBestLocation();
        return location;
    }

    /**
     * Part 2
     */
    public static long part2(Point from) throws IOException {
        AsteroidsMap map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputAsString(10, true),'#'));
        AsteroidsMap.Location loc = map.getVaporizedAsteroid(from, 200);
        return loc.point.x*100+loc.point.y;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            AsteroidsMap.Location loc = part1();
            Log.i(String.format("Number of asteroids that can be detected: %d", loc.visibleAsteroids));
            Log.i(String.format("Coordinates of 200th asteroid vaporized: %d", part2(loc.point)));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
