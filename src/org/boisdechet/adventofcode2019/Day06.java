package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.orbit.Object;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Solver for 2019 Day 6
 * https://adventofcode.com/2019/day/6
 */
public class Day06 {

    /**
     * Computes the distance from object to COM (object without parent/orbit)
     */
    private static final int getCount(Object obj) {
        int count = 0;
        while(obj.getInOrbitWith() != null) {
            count++;
            obj = obj.getInOrbitWith();
        }
        return count;
    }

    /**
     * Returns a map with all orbiting objects
     */
    public static Map<String, Object> getMap(BufferedReader input) throws IOException {
        String line = null;
        Map<String, Object> map = new HashMap<>();
        while ((line = input.readLine()) != null) {
            String[] values = line.split("\\)");
            Object parent = map.get(values[0]);
            Object child = map.get(values[1]);
            if(parent == null) {
                parent = new Object(values[0], null);
                map.put(parent.getKey(), parent);
            }
            if(child == null) {
                child = new Object(values[1], parent);
                map.put(child.getKey(), child);
            } else {
                child.setInOrbitWith(parent);
            }
        }
        return map;
    }

    /**
     * Computes the total number of direct and indirect orbits
     */
    public static int getResult(Map<String, Object> map) {
        int total = 0;
        for(Object o : map.values()) {
            total += getCount(o);
        }
        return total;
    }

    /**
     * Part 0 (sample)
     */
    public static long part0() throws IOException {
        BufferedReader input = InputUtil.readInputSample(6);
        return getResult(getMap(input));
    }

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        BufferedReader input = InputUtil.readInput(6, true);
        return getResult(getMap(input));
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        BufferedReader input = InputUtil.readInput(6, true);
        Map<String, Object> map = getMap(input);
        Map<String, Integer> distance = new HashMap<>();
        Object you = map.get("YOU");
        Object san = map.get("SAN");
        int curDist = 0;
        while(you.getInOrbitWith() != null) {
            distance.put(you.getInOrbitWith().getKey(), curDist);
            curDist++;
            you = you.getInOrbitWith();
        }
        curDist = 0;
        while(san.getInOrbitWith() != null) {
            if(distance.containsKey(san.getInOrbitWith().getKey())) {
                return curDist + distance.get(san.getInOrbitWith().getKey());
            }
            curDist++;
            san = san.getInOrbitWith();
        }
        throw new IllegalStateException("No common orbit found!");
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of direct and indirect orbits: %d", part1()));
            Log.i(String.format("Minimum number of orbital transfer: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
    }
}
