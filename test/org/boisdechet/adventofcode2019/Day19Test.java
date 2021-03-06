package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Beam;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.Vault;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day19Test {

    @Test
    public void examples() throws Exception {
        // solutions (backwards compatibility)
        assertEquals(118, Day19.part1());
        assertEquals(18651593, Day19.part2(1700, 1600)); // initial guess to speed up unit test
    }

}