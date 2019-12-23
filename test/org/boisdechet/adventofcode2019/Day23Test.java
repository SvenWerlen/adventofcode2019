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
//        long[] instr = InputUtil.convertToLongArray(InputUtil.readInputAsString(23, true));
//        NIC nic = new NIC(instr, 0, new Network(instr, 50));
//        nic.boot();
//        Thread.sleep(10000);
//        nic.shutdown();

        Log.INFO = true;
        long[] instr = InputUtil.convertToLongArray(InputUtil.readInputAsString(23, true));
        Network n = new Network(instr, 50);
        PointL p = n.runUntilPacketSentTo(255);
        Log.i("Final message = " + p.toString());
    }

}