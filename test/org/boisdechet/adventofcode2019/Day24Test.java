package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.bugs.Eris;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day24Test {

    @Test
    public void examples() throws Exception {
        // part I
        Eris e = new Eris(InputUtil.readSampleAsString(24, 0));
        assertEquals("....#\n#..#.\n#..##\n..#..\n#....\n", e.toString());
        e.oneMinuteLater();
        assertEquals("#..#.\n####.\n###.#\n##.##\n.##..\n", e.toString());
        e.oneMinuteLater();
        assertEquals("#####\n....#\n....#\n...#.\n#.###\n", e.toString());
        e.oneMinuteLater();
        assertEquals("#....\n####.\n...##\n#.##.\n.##.#\n", e.toString());
        e.oneMinuteLater();
        assertEquals("####.\n....#\n##..#\n.....\n##...\n", e.toString());
        e = new Eris(InputUtil.readSampleAsString(24, 0));
        e.untilFirstMatch();
        assertEquals(".....\n.....\n.....\n#....\n.#...\n", e.toString());
        assertEquals(2129920, e.getBiodiversityRating());
        // part II
        e = new Eris(InputUtil.readSampleAsString(24, 1), true);
        for(int i=0; i<10; i++) { e.oneMinuteLater(true, true); }
        assertEquals(InputUtil.readSampleAsString(24, 2), e.dump(true, true));
        assertEquals(99, e.bugsCount(true, true));
        // solutions (backwards compatibility)
        assertEquals(20751345, Day24.part1());
        assertEquals(1983, Day24.part2());
    }

}