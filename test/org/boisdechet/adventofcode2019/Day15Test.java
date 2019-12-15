package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Robot;
import org.boisdechet.adventofcode2019.coord.Ship;
import org.boisdechet.adventofcode2019.fuel.Reaction;
import org.boisdechet.adventofcode2019.fuel.Reactions;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day15Test {

    @Test
    public void examples() throws Exception {
        // solutions (backwards compatibility)
        assertEquals(210, Day15.part1());
        assertEquals(290, Day15.part2());
    }

}