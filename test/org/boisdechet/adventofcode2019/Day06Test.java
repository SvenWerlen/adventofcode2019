package org.boisdechet.adventofcode2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day06Test {

    @Test
    public void examples() throws Exception {
        assertEquals(42, Day06.part0());
        // solutions (backwards compatibility)
        assertEquals(151345, Day06.part1());
        assertEquals(391, Day06.part2());
    }

}