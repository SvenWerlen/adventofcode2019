package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.coord.Point;
import org.boisdechet.aventofcode2019.coord.Segment;
import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.boisdechet.aventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

public class Day04 {

    protected static boolean isValid(int number, boolean acceptLargerGroup) {
        String numberStr = String.valueOf(number);
        boolean hasAdjacent = false;
        int adjacent = 1;
        Character lastDigit = null;
        if(numberStr.length() != 6) {
            return false;
        }
        for(char c : numberStr.toCharArray()) {
            if(lastDigit != null && lastDigit > c) {
                return false;
            } else if(lastDigit != null && lastDigit == c) {
                if(acceptLargerGroup) {
                    hasAdjacent = true;
                }
                adjacent++;
            } else if(lastDigit != null) {
                if(!acceptLargerGroup && adjacent == 2) {
                    hasAdjacent = true;
                }
                adjacent = 1;
            }
            lastDigit = c;
        }
        // last two chars are identical
        if(!acceptLargerGroup && adjacent == 2) {
            return true;
        }
        return hasAdjacent;
    }

    protected static int getNumberOfValidPassword(String fromStr, String toStr, boolean acceptLargerGroup) {
        if(fromStr.length() != toStr.length()) {
            throw new IllegalStateException("Number lengths don't match! Not supported!");
        }
        int from = Integer.parseInt(fromStr);
        int to = Integer.parseInt(toStr);
        int count = 0;
        for(int i = from; i<=to; i++) {
            if(isValid(i, acceptLargerGroup)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        String input = InputUtil.readInputAsString(4, true);
        return getNumberOfValidPassword(input.split("-")[0], input.split("-")[1], true);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        String input = InputUtil.readInputAsString(4, true);
        return getNumberOfValidPassword(input.split("-")[0], input.split("-")[1], false);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of passwords meeting the criteria: %d", part1()));
            Log.i(String.format("Number of passwords meeting the criteria: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
