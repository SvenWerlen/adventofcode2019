package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.AsteroidsMap;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.opcode.EmergencyHull;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.opcode.Robot;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class Day11Test {

    @Test
    public void examples() throws Exception {
        // part I
        Robot rob = new Robot(new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(11, true))));
        EmergencyHull hull = new EmergencyHull();
        rob.paintAndMove(hull, new Robot.Output(1, 0));
        assertEquals(new Point(-1, 0), rob.getPosition());
        assertEquals(Robot.DIR_WEST, rob.getDirection());
        rob.paintAndMove(hull, new Robot.Output(0, 0));
        assertEquals(new Point(-1, 1), rob.getPosition());
        assertEquals(Robot.DIR_SOUTH, rob.getDirection());
        rob.paintAndMove(hull, new Robot.Output(1, 0));
        assertEquals(new Point(0, 1), rob.getPosition());
        assertEquals(Robot.DIR_EAST, rob.getDirection());
        rob.paintAndMove(hull, new Robot.Output(1, 0));
        assertEquals(new Point(0, 0), rob.getPosition());
        assertEquals(Robot.DIR_NORTH, rob.getDirection());
        rob.paintAndMove(hull, new Robot.Output(0, 1));
        rob.paintAndMove(hull, new Robot.Output(1, 0));
        rob.paintAndMove(hull, new Robot.Output(1, 0));
        assertEquals(new Point(0, -1), rob.getPosition());
        assertEquals(Robot.DIR_WEST, rob.getDirection());
        assertEquals(6, hull.paintedPanelCount());
        // part II
        // solutions (backwards compatibility)
        assertEquals(2319, Day11.part1());
        String result = "Hull:\n" +
                "                                             \n" +
                " .#..#.####.###..###..###..####..##....##..  \n" +
                "  #..#.#....#..#.#..#.#..#.#....#..#....#... \n" +
                "  #..#.###..#..#.#..#.#..#.###..#.......#... \n" +
                " .#..#.#....###..###..###..#....#.##....#..  \n" +
                " .#..#.#....#.#..#....#.#..#....#..#.#..#.   \n" +
                "  .##..####.#..#.#....#..#.#.....###..##..   \n" +
                "                                             \n";
        assertEquals(result, Day11.part2());
    }

}