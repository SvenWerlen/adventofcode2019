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
        // part II
        assertEquals(1L, ShuffleTechniques.shuffleCard(InputUtil.readSampleAsList(22, 0), 3, 10));
        assertEquals(0L, ShuffleTechniques.shuffleCard(InputUtil.readSampleAsList(22, 1), 3, 10));
        assertEquals(1L, ShuffleTechniques.shuffleCard(InputUtil.readSampleAsList(22, 2), 3, 10));
        assertEquals(8L, ShuffleTechniques.shuffleCard(InputUtil.readSampleAsList(22, 3), 3, 10));
        assertEquals(2480, ShuffleTechniques.shuffleCard(InputUtil.readInputAsList(22, true), 2019, 10007));
        // solutions (backwards compatibility)
        assertEquals(2480, Day22.part1());
        assertEquals(62416301438548L, Day22.part2());
    }

}