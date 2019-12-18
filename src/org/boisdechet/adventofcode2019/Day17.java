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
        // splitting was done manually. Couldn't find a way to program it
        //    A              B                 A              C               B                 C               A              C               B               C
        //    L8,R10,L10,    R10,L8,L8,L10,    L8,R10,L10,    L4,L6,L8,L8,    R10,L8,L8,L10,    L4,L6,L8,L8,    L8,R10,L10,    L4,L6,L8,L8,    R10,L8,L8,L10,  L4,L6,L8,L8
        String mainRoutine = "A,B,A,C,B,C,A,C,B,C\n";
        String fonctionA   = "L,8,R,10,L,10\n";
        String fonctionB   = "R,10,L,8,L,8,L,10\n";
        String fonctionC   = "L,4,L,6,L,8,L,8\n";
        String input = mainRoutine + fonctionA + fonctionB + fonctionC + "n\n";
        long[] instructions = InputUtil.convertToLongArray(InputUtil.readInputAsString(17,true));
        instructions[0] = 2;
        ScaffoldingMap map = new ScaffoldingMap(instructions, input);
        return map.getOutput();
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Sum of the alignment parameters: %d", part1()));
            Log.i(String.format("Dust collected by the vacuum robot: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
