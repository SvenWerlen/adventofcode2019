package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.computer.Network;
import org.boisdechet.adventofcode2019.coord.PointL;
import org.boisdechet.adventofcode2019.shuffle.ShuffleTechniques;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 23
 * https://adventofcode.com/2019/day/23
 */
public class Day23 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        long[] instr = InputUtil.convertToLongArray(InputUtil.readInputAsString(23, true));
        Network n = new Network(instr, 50);
        PointL p = n.runUntilPacketSentTo(255);
        return p.y;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        long[] instr = InputUtil.convertToLongArray(InputUtil.readInputAsString(23, true));
        Network n = new Network(instr, 50);
        PointL p = n.runUntilPacketSentTo0TwiceInARow();
        return p.y;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Y value of the first packet sent to address 255: %d", part1()));
            Log.i(String.format("First Y value delivered by the NAT to the computer at address 0 twice in a row: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
