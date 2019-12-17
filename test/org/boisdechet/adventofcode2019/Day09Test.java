package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Day09Test {

    @Test
    public void examples() throws Exception {
        // part I
        String instructions = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        OpCodeMachine machine = new OpCodeMachine(InputUtil.convertToLongArray(instructions));
        long code = OpCodeMachine.HALT;
        StringBuffer buf = new StringBuffer();
        do {
            buf.append(buf.length()==0 ? "": ",").append(code == OpCodeMachine.HALT ? "" : code);
            code = machine.execute(0);
        } while (code != OpCodeMachine.HALT);
        assertEquals(instructions, buf.toString());
        machine = new OpCodeMachine(InputUtil.convertToLongArray("1102,34915192,34915192,7,4,7,99,0"));
        assertEquals(16, String.valueOf(machine.execute(0)).length());
        machine = new OpCodeMachine(InputUtil.convertToLongArray("104,1125899906842624,99"));
        assertEquals(1125899906842624L, machine.execute(0));
        // solutions (backwards compatibility)
        assertEquals(2350741403L, Day09.part1());
        assertEquals(53088L, Day09.part2());
    }

}