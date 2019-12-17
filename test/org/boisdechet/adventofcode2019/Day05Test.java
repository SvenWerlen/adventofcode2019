package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.OpCode;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day05Test {

    @Test
    public void testOpCode() {
        assertTrue((new OpCode(1)).isValid());
        assertEquals(OpCode.OP_ADD, (new OpCode(1)).getOpCode());
        assertTrue((new OpCode(2)).isValid());
        assertEquals(OpCode.MODE_POSITION, (new OpCode(2)).getParam1Mode());
        assertTrue((new OpCode(3)).isValid());
        assertTrue((new OpCode(4)).isValid());
        assertFalse((new OpCode(11)).isValid());
        assertTrue((new OpCode(99)).isValid());
        assertEquals(OpCode.OP_HALT, (new OpCode(99)).getOpCode());
        assertTrue((new OpCode(104)).isValid());
        assertEquals(OpCode.MODE_IMMEDIATE, (new OpCode(104)).getParam1Mode());
        assertFalse((new OpCode(304)).isValid());
        assertTrue((new OpCode(1004)).isValid());
        assertEquals(OpCode.MODE_IMMEDIATE, (new OpCode(1004)).getParam2Mode());
        assertFalse((new OpCode(5004)).isValid());
        assertTrue((new OpCode(11004)).isValid());
        assertEquals(OpCode.MODE_IMMEDIATE, (new OpCode(11004)).getParam3Mode());
        assertFalse((new OpCode(81004)).isValid());
        assertTrue((new OpCode(211004)).isValid());
        assertFalse((new OpCode(-12)).isValid());
    }

    @Test
    public void examples() throws Exception {
        OpCodeMachine m = new OpCodeMachine(InputUtil.convertToLongArray("3,9,8,9,10,9,4,9,99,-1,8"));
        assertEquals(0, m.execute(5));
        assertEquals(1, m.reset().execute(8));
        assertEquals(0, m.reset().execute(9));
        m = new OpCodeMachine(InputUtil.convertToLongArray("3,9,7,9,10,9,4,9,99,-1,8"));
        assertEquals(1, m.execute(5));
        assertEquals(0, m.reset().execute(8));
        assertEquals(0, m.reset().execute(9));
        m = new OpCodeMachine(InputUtil.convertToLongArray("3,3,1108,-1,8,3,4,3,99"));
        assertEquals(0, m.execute(5));
        assertEquals(1, m.reset().execute(8));
        assertEquals(0, m.reset().execute(9));
        m = new OpCodeMachine(InputUtil.convertToLongArray("3,3,1107,-1,8,3,4,3,99"));
        assertEquals(1, m.execute(5));
        assertEquals(0, m.reset().execute(8));
        assertEquals(0, m.reset().execute(9));
        m = new OpCodeMachine(InputUtil.convertToLongArray("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"));
        assertEquals(999, m.execute(5));
        assertEquals(1000, m.reset().execute(8));
        assertEquals(1001, m.reset().execute(9));
        // solutions (backwards compatibility)
        assertEquals(13787043, Day05.part1());
        assertEquals(3892695, Day05.part2());
    }

}