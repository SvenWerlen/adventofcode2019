package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.AsteroidsMap;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.opcode.EmergencyHull;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.opcode.Robot;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 11
 * https://adventofcode.com/2019/day/11
 */
public class Day11 {

    public static int DIR_NORTH = 0;
    public static int DIR_EAST = 1;
    public static int DIR_SOUTH = 2;
    public static int DIR_WEST = 3;

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        EmergencyHull hull = new EmergencyHull();
        Robot rob = new Robot(new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(11, true))));
        Robot.Output out = rob.next(0);
        while(out != null) {
            rob.paintAndMove(hull, out);
            out = rob.next(hull.getColor(rob.getPosition()));
        }
        return hull.paintedPanelCount();
    }

    /**
     * Part 2
     */
    public static String part2() throws IOException {
        EmergencyHull hull = new EmergencyHull();
        Robot rob = new Robot(new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(11, true))));
        Robot.Output out = rob.next(1);
        while(out != null) {
            rob.paintAndMove(hull, out);
            out = rob.next(hull.getColor(rob.getPosition()));
        }
        return hull.toString();
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of panels painted at least once: %d", part1()));
            Log.i(part2());
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
