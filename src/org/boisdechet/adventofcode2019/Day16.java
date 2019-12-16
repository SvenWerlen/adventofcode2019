package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Robot;
import org.boisdechet.adventofcode2019.coord.Ship;
import org.boisdechet.adventofcode2019.fft.FlawedFrequencyTransmission;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 16
 * https://adventofcode.com/2019/day/16
 */
public class Day16 {

    /**
     * Part 1
     */
    public static String part1() throws IOException {
        return new FlawedFrequencyTransmission(InputUtil.convertToDigitArray(InputUtil.readInputAsString(16, true))).cleanSignal(100);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        //Ship ship = new Ship(new Robot(InputUtil.convertToLongArray(InputUtil.readInputAsString(15,true))));
        return 0L;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("First eight digits in the final output list: %s", part1()));
            //Log.i(String.format("Minutes it takes to fill with oxygen: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
