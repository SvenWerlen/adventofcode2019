package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.ScaffoldingMap;
import org.boisdechet.adventofcode2019.fft.FlawedFrequencyTransmission;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 17
 * https://adventofcode.com/2019/day/17
 */
public class Day17 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        ScaffoldingMap map = new ScaffoldingMap(InputUtil.convertToLongArray(InputUtil.readInputAsString(17,true)));
        map.buildPath();
        return map.alignParametersSum();
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        return 0;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Sum of the alignment parameters: %d", part1()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
