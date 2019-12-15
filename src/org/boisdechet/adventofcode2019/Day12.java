package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Moon;
import org.boisdechet.adventofcode2019.coord.Moons;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Solver for 2019 Day 12
 * https://adventofcode.com/2019/day/12
 */
public class Day12 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        List<Moon> moons = new ArrayList<Moon>();
        BufferedReader input = InputUtil.readInput(12, true);
        String line = "";
        while ((line = input.readLine()) != null) {
            moons.add(new Moon(line));
        }
        // 1000 steps
        for(int i=0; i<1000; i++) {
            Moons.step(moons);
        }
        return Moons.getTotalEnergy(moons);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        List<Moon> moons = new ArrayList<Moon>();
        BufferedReader input = InputUtil.readInput(12, true);
        String line = "";
        while ((line = input.readLine()) != null) {
            moons.add(new Moon(line));
        }
        return Moons.getStepsCountForFirstMatch(moons);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Total energy of the system after 1000 steps: %d", part1()));
            Log.i(String.format("Steps to reach first state: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
