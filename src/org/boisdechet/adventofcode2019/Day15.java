package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Robot;
import org.boisdechet.adventofcode2019.coord.Ship;
import org.boisdechet.adventofcode2019.fuel.Reaction;
import org.boisdechet.adventofcode2019.fuel.Reactions;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Solver for 2019 Day 15
 * https://adventofcode.com/2019/day/15
 */
public class Day15 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        Ship ship = new Ship(new Robot(InputUtil.convertToLongArray(InputUtil.readInputAsString(15,true))));
        return ship.searchOxygen();
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        Ship ship = new Ship(new Robot(InputUtil.convertToLongArray(InputUtil.readInputAsString(15,true))));
        return ship.fillOxygen();
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Fewest number of movement commands to oxygen system: %d", part1()));
            Log.i(String.format("Minutes it takes to fill with oxygen: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
