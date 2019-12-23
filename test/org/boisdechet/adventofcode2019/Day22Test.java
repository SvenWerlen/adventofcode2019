package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.shuffle.ShuffleTechniques;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Day22Test {

    @Test
    public void examples() throws Exception {
        // part I
        assertArrayEquals(new int[] {0, 3, 6, 9, 2, 5, 8, 1, 4, 7}, ShuffleTechniques.shuffle(InputUtil.readSampleAsList(22, 0), 10));
        assertArrayEquals(new int[] {3, 0, 7, 4, 1, 8, 5, 2, 9, 6}, ShuffleTechniques.shuffle(InputUtil.readSampleAsList(22, 1), 10));
        assertArrayEquals(new int[] {6, 3, 0, 7, 4, 1, 8, 5, 2, 9}, ShuffleTechniques.shuffle(InputUtil.readSampleAsList(22, 2), 10));
        assertArrayEquals(new int[] {9, 2, 5, 8, 1, 4, 7, 0, 3, 6}, ShuffleTechniques.shuffle(InputUtil.readSampleAsList(22, 3), 10));
        // solutions (backwards compatibility)
        assertEquals(2480, Day22.part1());
    }

}