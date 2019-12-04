package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.boisdechet.aventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Day02Test {

    @Test
    public void examples() {
        //Log.DEBUG = true;
        final String SAMPLE = "1,9,10,3,2,3,11,0,99,30,40,50";
        // utils
        assertArrayEquals(new int[]{1,9,10,3,2,3,11,0,99,30,40,50}, InputUtil.convertToIntArray(SAMPLE));
        assertEquals(3500, Day02.executeIntCode(InputUtil.convertToIntArray(SAMPLE)));
    }

}