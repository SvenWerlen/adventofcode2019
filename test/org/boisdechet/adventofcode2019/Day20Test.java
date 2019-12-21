package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.DonutMaze;
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
        // solutions (backwards compatibility)
        assertEquals(690, Day20.part1());
    }

}