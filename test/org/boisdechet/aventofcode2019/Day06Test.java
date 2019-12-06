package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.opcode.OpCode;
import org.boisdechet.aventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class Day06Test {

    @Test
    public void examples() throws IOException {
        assertEquals(42, Day06.part0());
    }

}