package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.DonutMaze;
import org.boisdechet.adventofcode2019.coord.DonutMaze2;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day20Test {

    @Test
    public void examples() throws Exception {
        // part I
        assertEquals(23, new DonutMaze(InputUtil.readSampleAsString(20, 0)).getShortestPath().getDistance());
        assertEquals(58, new DonutMaze(InputUtil.readSampleAsString(20, 1)).getShortestPath().getDistance());
        // part II
        assertEquals(396, new DonutMaze2(InputUtil.readSampleAsString(20, 2), true).getShortestPath().getDistance());
        // solutions (backwards compatibility)
        assertEquals(690, Day20.part1());
        assertEquals(7976, Day20.part2());
    }

}