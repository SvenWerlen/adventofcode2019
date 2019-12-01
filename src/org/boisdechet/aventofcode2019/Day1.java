package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.boisdechet.aventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Solver for 2019 Day 1
 * https://adventofcode.com/2019/day/1
 */
public class Day1 {

    /**
     * Utility to compute fuel: floor(mass/3)-2
     */
    protected static long computeFuel(long mass) {
        long fuel = new Double(Math.floor(mass / 3d) -2).longValue();
        return fuel > 0 ? fuel : 0;
    }

    /**
     * Utility to compute total fuel (see part2)
     */
    protected static long computeTotalFuel(long mass) {
        long totalFuel = 0;
        while(mass > 0) {
            long fuel = computeFuel(mass);
            totalFuel += fuel;
            mass = fuel;
        }
        return totalFuel;
    }

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        BufferedReader input = InputUtil.readInput(1, true);
        String line = "";
        long totalFuel = 0;
        while ((line = input.readLine()) != null) {
            // extract mass
            long mass = Long.parseLong(line);
            // compute fuel
            totalFuel += computeFuel(mass);
        }
        return totalFuel;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        BufferedReader input = InputUtil.readInput(1, true); // same input!
        String line = "";
        long totalFuel = 0;
        while ((line = input.readLine()) != null) {
            // extract mass
            long mass = Long.parseLong(line);
            // compute fuel
            totalFuel += computeTotalFuel(mass);
        }
        return totalFuel;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Total fuel (part 1): %d", part1()));
            Log.i(String.format("Total fuel (part 2): %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
    }
}
