package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.boisdechet.aventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Day02Test {

    @Test
    public void examples() throws Exception {
        //Log.DEBUG = true;
        final String SAMPLE = "1,9,10,3,2,3,11,0,99,30,40,50";
        // utils
        assertArrayEquals(new int[]{1,9,10,3,2,3,11,0,99,30,40,50}, InputUtil.convertToIntArray(SAMPLE));
        assertEquals(3500, Day02.executeIntCode(InputUtil.convertToIntArray(SAMPLE)));
        // solutions (backwards compatibility)
        assertEquals(7210630, Day02.part1());
        assertEquals(3892, Day02.part2());
    }

}