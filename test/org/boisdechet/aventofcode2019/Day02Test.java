package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Day02Test {

    @Test
    public void examples() {
        Log.DEBUG = true;
        final String SAMPLE = "1,9,10,3,2,3,11,0,99,30,40,50";
        // utils
        assertArrayEquals(new int[]{1,9,10,3,2,3,11,0,99,30,40,50}, Day02.convertToIntCode(SAMPLE));
        assertEquals(3500, Day02.executeIntCode(Day02.convertToIntCode(SAMPLE)));
        // part 1
        //assertEquals(2, Day01.computeFuel(12));
        // part 2
    }

}