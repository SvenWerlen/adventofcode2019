package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.DonutMaze;
import org.boisdechet.adventofcode2019.coord.DonutMaze2;
import org.boisdechet.adventofcode2019.opcode.OpCodeAscii;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;

/**
 * Solver for 2019 Day 21
 * https://adventofcode.com/2019/day/21
 */
public class Day21 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        // instructions for jumping if hole in any next 3 tiles BUT ground on 4th tile (in order to not fall into it)
        // (!A or !B or !C) and D
        String instr = "NOT A J\n" +
                "NOT B T\n" +
                "OR T J\n" +
                "NOT C T\n" +
                "OR T J\n" +
                "AND D J\n" +
                "WALK\n";
        OpCodeMachine m = new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(21,true)), OpCodeAscii.getInput(instr));
        long code;
        while((code = (long)m.execute(0)) != OpCodeMachine.HALT) {
            if(code < 256) {
                //System.out.print((char) code);
            } else {
                return code;
            }
        }
        return -1;
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        // same as before but ensure make sure that 2nd jump is possible right after
        // (!A or !B or !C) and D and (H or E)
        String instr = "NOT A J\n" +
                "NOT B T\n" +
                "OR T J\n" +
                "NOT C T\n" +
                "OR T J\n" +
                "AND D J\n" +
                "NOT H T\n" +
                "NOT T T\n" +
                "OR E T\n" +
                "AND T J\n" +
                "RUN\n";
        OpCodeMachine m = new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(21,true)), OpCodeAscii.getInput(instr));
        long code;
        while((code = (long)m.execute(0)) != OpCodeMachine.HALT) {
            if(code < 256) {
                //System.out.print((char) code);
            } else {
                return code;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Amount of hull damage (WALK): %d", part1()));
            Log.i(String.format("Amount of hull damage (RUN): %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
