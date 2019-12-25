package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.computer.NIC;
import org.boisdechet.adventofcode2019.computer.Network;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.PointL;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.shuffle.ShuffleTechniques;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class Day23Test {

    @Test
    public void examples() throws Exception {
        // solutions (backwards compatibility)
        assertEquals(18604, Day23.part1());
        assertEquals(11880, Day23.part2());
    }

}