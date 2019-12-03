package org.boisdechet.aventofcode2019;

import org.boisdechet.aventofcode2019.utils.InputUtil;
import org.boisdechet.aventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Solver for 2019 Day 2
 * https://adventofcode.com/2019/day/2
 */
public class Day02 {

    /**
     * Utility to convert instructions (as string) to IntCode
     */
    protected static int[] convertToIntCode(String instructions) {
        String[] instr = instructions.split(",");
        int[] result = new int[instr.length];
        for(int i=0; i<instr.length; i++) {
            result[i]=Integer.parseInt(instr[i]);
        }
        return result;
    }

    /**
     * Utility to convert execute instructions
     */
    protected static int executeIntCode(int[] instructions) {
        int curIdx = 0;
        while(true) {
            Log.d(instructions);
            // check for errors
            if(curIdx < 0 || curIdx+3 >= instructions.length) {
                throw new IllegalStateException(String.format("Trying to access index %d of %d instructions!", curIdx, instructions.length));
            }
            int opcode = instructions[curIdx];
            Log.d(String.format("Opcode is: %d", opcode));
            if(opcode == 99) {
                return instructions[0];
            }

            int value1 = instructions[instructions[curIdx+1]]; // could throw an OutOfBoundException
            int value2 = instructions[instructions[curIdx+2]]; // could throw an OutOfBoundException
            int resIdx = instructions[curIdx+3];
            switch(opcode) {
                case 1:
                    instructions[resIdx] = value1 + value2;
                    break;
                case 2:
                    instructions[resIdx] = value1 * value2;
                    break;
                default:
                    throw new IllegalStateException(String.format("Opcode %d is invalid!", opcode));
            }
            curIdx += 4;
        }
    }

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        String val = InputUtil.readInputAsString(2, true);
        int[] intCode = convertToIntCode(val);
        // 1202 program alarm
        intCode[1] = 12;
        intCode[2] = 2;
        return executeIntCode(intCode);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        String val = InputUtil.readInputAsString(2, true);
        int desiredOutput = Integer.parseInt(InputUtil.readInputAsString(2, false));
        for(int noun = 0; noun < 100; noun++) {
            for(int verb = 0; verb < 100; verb++) {
                int[] intCode = convertToIntCode(val);
                intCode[1] = noun;
                intCode[2] = verb;
                int output = executeIntCode(intCode);
                if(output == desiredOutput) {
                    return (100 * noun) + verb;
                }
            }
        }
        throw new IllegalStateException("Result not found :-(");
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Left value at position 0: %d", part1()));
            Log.i(String.format("Answer is: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
    }
}
