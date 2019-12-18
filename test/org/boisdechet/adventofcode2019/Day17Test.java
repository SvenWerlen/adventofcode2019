package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.ScaffoldingMap;
import org.boisdechet.adventofcode2019.fft.FlawedFrequencyTransmission;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day17Test {

    @Test
    public void examples() throws Exception {
        long[] input = InputUtil.convertToLongArray(InputUtil.readInputAsString(17,true));
        ScaffoldingMap map = new ScaffoldingMap(input);
        List<Point> path = map.buildPath();
        List<ScaffoldingMap.Step> steps = ScaffoldingMap.stepsFromPath(path);
        StringBuffer buf = new StringBuffer();
        for(ScaffoldingMap.Step s : steps) {
            buf.append(s.toString()).append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        assertEquals("L8,R10,L10,R10,L8,L8,L10,L8,R10,L10,L4,L6,L8,L8,R10,L8,L8,L10,L4,L6,L8,L8,L8,R10,L10,L4,L6,L8,L8,R10,L8,L8,L10,L4,L6,L8,L7", buf.toString());
        // solutions (backwards compatibility)
        assertEquals(5940, Day17.part1());
        assertEquals(923795, Day17.part2());
    }

}