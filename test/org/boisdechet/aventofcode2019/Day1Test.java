package org.boisdechet.aventofcode2019;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day1Test {

    @Test
    public void examples() {
        // part 1
        assertEquals(2, Day1.computeFuel(12));
        assertEquals(2, Day1.computeFuel(14));
        assertEquals(654, Day1.computeFuel(1969));
        assertEquals(33583, Day1.computeFuel(100756));
        // part 2
        assertEquals(2, Day1.computeTotalFuel(14));
        assertEquals(966, Day1.computeTotalFuel(1969));
        assertEquals(50346, Day1.computeTotalFuel(100756));
    }

}