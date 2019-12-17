package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class OpCodeMachine implements Cloneable {

    public static final long HALT = Integer.MIN_VALUE;

    private long[] orig;
    private long[] instr;
    private int curInstr;
    private int relBase;
    private int paramInit;
    private boolean paramInitUsed;
    Map<Integer, Long> outMemory;

    private OpCodeMachine() {
    }

    public OpCodeMachine(long[] instructions) {
        orig = instructions;
        instr = new long[this.orig.length];
        System.arraycopy(this.orig, 0, instr, 0, this.orig.length);
        outMemory = new HashMap<>();
        curInstr = 0;
        relBase = 0;
        paramInit = -1;
        paramInitUsed = false;
    }

    public OpCodeMachine(long[] instructions, int paramInit) {
        this(instructions);
        this.paramInit = paramInit;
    }

    /**
     * Returns the value based on mode
     */
    private long getValue(long value, int mode) {
        if(mode == OpCode.MODE_IMMEDIATE) {
            return value;
        } else if(mode == OpCode.MODE_POSITION) {
            int pos = Math.toIntExact(value);
            if(pos < instr.length) {
                return instr[pos];
            } else if(outMemory.containsKey(pos)) {
                return outMemory.get(pos);
            } else {
                return 0;
            }
        } else if(mode == OpCode.MODE_RELATIVE) {
            int pos = Math.toIntExact(value + relBase);
            if(pos < instr.length) {
                return instr[pos];
            } else if(outMemory.containsKey(pos)) {
                return outMemory.get(pos);
            } else {
                return 0;
            }
        } else {
            throw new IllegalStateException(String.format("Illegal OpCode mode %d", mode));
        }
    }

    /**
     * Sets value at given position
     */
    private void setValue(int pos, long value, int mode) {
        if(mode == OpCode.MODE_RELATIVE) {
            pos += relBase;
        }
        if (pos < instr.length) {
            instr[pos] = value;
        } else {
            outMemory.put(pos, value);
            Log.d(String.format("OutMemory [%d] set to %d", pos, value));
        }
    }

    /**
     * Checks condition or throw IllegalStateException
     */
    private void check(boolean value, String errorMsg) {
        if(!value) {
            throw new IllegalStateException(errorMsg);
        }
    }

    public OpCodeMachine reset() {
        // reset instructions (that might have changed from previous execution)
        instr = new long[this.orig.length];
        System.arraycopy(this.orig, 0, instr, 0, this.orig.length);
        curInstr = 0;
        relBase = 0;
        return this;
    }

    public long execute(int parameter) {
        long result = 0;
        while(true) {
            Log.d(instr);

            // get OpCode
            long opcodeInt = instr[curInstr];
            OpCode opcode = new OpCode(opcodeInt);
            Log.d("");
            Log.d(String.format("Opcode is: %s (%d)", opcode.toString(), opcodeInt));
            check(opcode.isValid(),String.format("Invalid opcode: %d", opcodeInt));

            // execute
            switch(opcode.getOpCode()) {
                case OpCode.OP_ADD: // 1
                    check(opcode.getParam3Mode() != OpCode.MODE_IMMEDIATE, "Invalid mode for 'Add' instruction.");
                    long val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    long val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    int dest =  Math.toIntExact(instr[curInstr+3]);
                    Log.d(String.format("Add: %d + %d => [%d]", val1, val2, dest));
                    setValue(dest, val1 + val2, opcode.getParam3Mode());
                    curInstr += 4;
                    break;
                case OpCode.OP_MULT: // 2
                    check(opcode.getParam3Mode() != OpCode.MODE_IMMEDIATE, "Invalid mode for 'Mult' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest =  Math.toIntExact(instr[curInstr+3]);
                    Log.d(String.format("Mult: %d * %d => [%d]", val1, val2, dest));
                    setValue(dest, val1 * val2, opcode.getParam3Mode());
                    curInstr += 4;
                    break;
                case OpCode.OP_IN: // 3
                    check(opcode.getParam1Mode() != OpCode.MODE_IMMEDIATE, "Invalid mode for 'In' instruction.");
                    dest = Math.toIntExact(instr[curInstr+1]);
                    int param = !paramInitUsed && paramInit >= 0  ? this.paramInit : parameter;
                    paramInitUsed = true;
                    setValue(dest, param, opcode.getParam1Mode());
                    Log.d(String.format("In: [%d] = %d", dest, param));
                    curInstr += 2;
                    break;
                case OpCode.OP_OUT: // 4
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    Log.d(String.format("[Out] %d (index %d)", val1, curInstr));
                    result = val1;
                    curInstr += 2;
                    return result; // 5
                case OpCode.OP_JUMP_IF_TRUE:
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    if(val1 != 0) {
                        Log.d(String.format("%d == 1 ? YES. Jumping to %d", val1, val2));
                        curInstr = Math.toIntExact(val2);
                    } else {
                        Log.d(String.format("%d == 1 ? NO. Continuing.", val1, val2));
                        curInstr += 3;
                    }
                    break;
                case OpCode.OP_JUMP_IF_FALSE: // 6
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    if(val1 == 0) {
                        Log.d(String.format("%d == 0 ? YES. Jumping to %d", val1, val2));
                        curInstr = Math.toIntExact(val2);
                    } else {
                        Log.d(String.format("%d == 0 ? NO. Continuing.", val1, val2));
                        curInstr += 3;
                    }
                    break;
                case OpCode.OP_LESS_THAN: // 7
                    check(opcode.getParam3Mode() != OpCode.MODE_IMMEDIATE, "Invalid mode for 'Less than' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest = Math.toIntExact(instr[curInstr+3]);
                    Log.d(String.format("%d < %d ? [%d]=%d", val1, val2, dest, val1 < val2 ? 1 : 0));
                    setValue(dest, val1 < val2 ? 1 : 0, opcode.getParam3Mode());
                    curInstr += 4;
                    break;
                case OpCode.OP_EQUALS: // 8
                    check(opcode.getParam3Mode() != OpCode.MODE_IMMEDIATE, "Invalid mode for 'Equals' instruction.");
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    val2 = getValue(instr[curInstr+2], opcode.getParam2Mode());
                    dest = Math.toIntExact(instr[curInstr+3]);
                    Log.d(String.format("%d == %d ? [%d]=%d", val1, val2, dest, val1 == val2 ? 1 : 0));
                    setValue(dest, val1 == val2 ? 1 : 0, opcode.getParam3Mode());
                    curInstr += 4;
                    break;
                case OpCode.OP_RELBASE: // 9
                    val1 = getValue(instr[curInstr+1], opcode.getParam1Mode());
                    relBase += val1;
                    Log.d(String.format("RelBase increased by %d (now %d)", val1, relBase));
                    curInstr += 2;
                    break;
                case OpCode.OP_HALT:
                    return HALT;
                default:
                    throw new IllegalStateException(String.format("Opcode %d is invalid!", opcode.getOpCode()));
            }
        }
    }

    @Override
    public OpCodeMachine clone() {
        OpCodeMachine machine = new OpCodeMachine();
        // copy instructions
        machine.orig = new long[this.orig.length];
        System.arraycopy(this.orig, 0, machine.orig, 0, this.orig.length);
        machine.instr = new long[this.instr.length];
        System.arraycopy(this.instr, 0, machine.instr, 0, this.instr.length);
        machine.curInstr = this.curInstr;
        machine.relBase = this.relBase;
        machine.paramInit = this.paramInit;
        machine.paramInitUsed = this.paramInitUsed;
        // copy out memory
        machine.outMemory = new HashMap<>();
        for(Integer key : this.outMemory.keySet()) {
            machine.outMemory.put(key, this.outMemory.get(key));
        }
        return machine;
    }
}
