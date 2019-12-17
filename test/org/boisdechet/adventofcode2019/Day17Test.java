package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.ScaffoldingMap;
import org.boisdechet.adventofcode2019.fft.FlawedFrequencyTransmission;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day17Test {

    @Test
    public void examples() throws Exception {
        // solutions (backwards compatibility)
        assertEquals(5940, Day17.part1());
    }

}