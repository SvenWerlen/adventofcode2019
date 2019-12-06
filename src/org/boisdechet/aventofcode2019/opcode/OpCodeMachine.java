package org.boisdechet.aventofcode2019.opcode;

import org.boisdechet.aventofcode2019.utils.Log;

public class OpCodeMachine {

    private int[] orig;
    private int[] instr;

    public OpCodeMachine(int[] instructions) {
        this.orig = instructions;
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

    public int execute(int parameter) {
        // reset instructions (that might have changed from previous execution)
        instr = new int[this.orig.length];
        System.arraycopy( this.orig, 0, instr, 0, this.orig.length );

        int curIdx = 0;
        int result = 0;
        while(true) {
            Log.d(instr);

            // get OpCode
            int opcodeInt = instr[curIdx];
            Log.d(String.format("Opcode is: %d", opcodeInt));
            OpCode opcode = new OpCode(opcodeInt);
            check(opcode.isValid(),String.format("Invalid opcode: %d", opcodeInt));

            // execute
            switch(opcode.getOpCode()) {
                case OpCode.OP_ADD:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Add' instruction.");
                    int val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    int val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    int dest = instr[curIdx+3];
                    instr[dest] = val1 + val2;
                    curIdx += 4;
                    break;
                case OpCode.OP_MULT:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Mult' instruction.");
                    val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    dest = instr[curIdx+3];
                    instr[dest] = val1 * val2;
                    curIdx += 4;
                    break;
                case OpCode.OP_IN:
                    dest = instr[curIdx+1];
                    instr[dest] = parameter;
                    curIdx += 2;
                    break;
                case OpCode.OP_OUT:
                    dest = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    Log.i(String.format("[Out] %d (index %d)", dest, curIdx));
                    result = dest;
                    curIdx += 2;
                    break;
                case OpCode.OP_JUMP_IF_TRUE:
                    val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    if(val1 != 0) {
                        Log.d(String.format("Jumping to %d", val2));
                        curIdx = val2;
                    } else {
                        curIdx += 3;
                    }
                    break;
                case OpCode.OP_JUMP_IF_FALSE:
                    val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    if(val1 == 0) {
                        Log.d(String.format("Jumping to %d", val2));
                        curIdx = val2;
                    } else {
                        curIdx += 3;
                    }
                    break;
                case OpCode.OP_LESS_THAN:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Less than' instruction.");
                    val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    dest = instr[curIdx+3];
                    instr[dest] = val1 < val2 ? 1 : 0;
                    curIdx += 4;
                    break;
                case OpCode.OP_EQUALS:
                    check(opcode.getParam3Mode() == OpCode.MODE_POSITION, "Invalid mode for 'Equals' instruction.");
                    val1 = getValue(instr[curIdx+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curIdx+2], opcode.getParam2Mode());
                    dest = instr[curIdx+3];
                    instr[dest] = val1 == val2 ? 1 : 0;
                    curIdx += 4;
                    break;
                case OpCode.OP_HALT:
                    return result;
                default:
                    throw new IllegalStateException(String.format("Opcode %d is invalid!", opcode.getOpCode()));
            }
        }
    }
}
