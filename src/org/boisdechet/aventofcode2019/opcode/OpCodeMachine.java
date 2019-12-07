package org.boisdechet.aventofcode2019.opcode;

import org.boisdechet.aventofcode2019.utils.Log;

public class OpCodeMachine {

    private int[] orig;
    private int[] instr;
    private int curInstr = 0;

    public OpCodeMachine(int[] instructions) {
        this.orig = instructions;
        instr = new int[this.orig.length];
        System.arraycopy(this.orig, 0, instr, 0, this.orig.length);
    }

    /**
     * Returns the value based on mode
     */
    private int getValue(int value, int mode) {
        return mode == OpCode.MODE_IMMEDIATE ? value : instr[value];
    }

    /**
     * Checks condition or throw IllegalStateException
     */
    private void check(boolean value, String errorMsg) {
        if(!value) {
            throw new IllegalStateException(errorMsg);
        }
    }

    /**
     * 1-parameter version
     */
    public int execute(int parameter) {
        return execute(parameter, parameter);
    }

    public int execute(int parameterInit, int parameter2) {
        return execute(parameterInit, parameter2, true);
    }

    public int execute(int parameterInit, int parameter2, boolean reset) {
        // reset instructions (that might have changed from previous execution)
        if(reset) {
            instr = new int[this.orig.length];
            System.arraycopy(this.orig, 0, instr, 0, this.orig.length);
            curInstr = 0;
        }

        int result = 0;
        int parameterCount = 0;
        while(true) {
            Log.d(instr);

            // get OpCode
            int opcodeInt = instr[curInstr];
            OpCode opcode = new OpCode(opcodeInt);
            Log.d(String.format("Opcode is: %s", opcode.toString()));
            check(opcode.isValid(),String.format("Invalid opcode: %d", opcodeInt));

            // execute
            switch(opcode.getOpCode()) {
                case OpCode.OP_ADD:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Add' instruction.");
                    int val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    int val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    int dest = instr[curInstr+3];
                    instr[dest] = val1 + val2;
                    curInstr += 4;
                    break;
                case OpCode.OP_MULT:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Mult' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest = instr[curInstr+3];
                    instr[dest] = val1 * val2;
                    curInstr += 4;
                    break;
                case OpCode.OP_IN:
                    dest = instr[curInstr+1];
                    instr[dest] = parameterCount == 0  ? parameterInit : parameter2;
                    parameterCount++;
                    curInstr += 2;
                    break;
                case OpCode.OP_OUT:
                    dest = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    Log.d(String.format("[Out] %d (index %d)", dest, curInstr));
                    result = dest;
                    curInstr += 2;
                    return result;
                case OpCode.OP_JUMP_IF_TRUE:
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    if(val1 != 0) {
                        Log.d(String.format("Jumping to %d", val2));
                        curInstr = val2;
                    } else {
                        curInstr += 3;
                    }
                    break;
                case OpCode.OP_JUMP_IF_FALSE:
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    if(val1 == 0) {
                        Log.d(String.format("Jumping to %d", val2));
                        curInstr = val2;
                    } else {
                        curInstr += 3;
                    }
                    break;
                case OpCode.OP_LESS_THAN:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Less than' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest = instr[curInstr+3];
                    instr[dest] = val1 < val2 ? 1 : 0;
                    curInstr += 4;
                    break;
                case OpCode.OP_EQUALS:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Equals' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest = instr[curInstr+3];
                    instr[dest] = val1 == val2 ? 1 : 0;
                    curInstr += 4;
                    break;
                case OpCode.OP_HALT:
                    return 0;
                default:
                    throw new IllegalStateException(String.format("Opcode %d is invalid!", opcode.getOpCode()));
            }
        }
    }
}
