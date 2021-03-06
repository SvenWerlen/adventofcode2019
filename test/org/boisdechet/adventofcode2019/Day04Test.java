package org.boisdechet.adventofcode2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day04Test {


    @Test
    public void examples() throws Exception {
        //Log.DEBUG = true;
        // part 1
        assertTrue(Day04.isValid(111111, true));
        assertFalse(Day04.isValid(223450, true));
        assertFalse(Day04.isValid(123789, true));
        // part 2
        assertTrue(Day04.isValid(112233, false));
        assertFalse(Day04.isValid(123444, false));
        assertTrue(Day04.isValid(111122, false));
        assertFalse(Day04.isValid(588889, false));
        assertTrue(Day04.isValid(112233, false));
        // solutions (backwards compatibility)
        assertEquals(1748, Day04.part1());
        assertEquals(1180, Day04.part2());
    }

}