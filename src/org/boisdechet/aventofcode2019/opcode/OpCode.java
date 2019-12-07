package org.boisdechet.aventofcode2019.opcode;

public class OpCode {

    public static final int MODE_POSITION    = 0;
    public static final int MODE_IMMEDIATE   = 1;
    public static final int OP_ADD           = 1;
    public static final int OP_MULT          = 2;
    public static final int OP_IN            = 3;
    public static final int OP_OUT           = 4;
    public static final int OP_JUMP_IF_TRUE  = 5;
    public static final int OP_JUMP_IF_FALSE = 6;
    public static final int OP_LESS_THAN     = 7;
    public static final int OP_EQUALS        = 8;
    public static final int OP_HALT = 99;

    private int opCode;
    private int param1Mode;
    private int param2Mode;
    private int param3Mode;

    public OpCode(int opcode) {
        this.opCode = opcode % 100;
        this.param1Mode = (opcode / 100) % 10;
        this.param2Mode = (opcode / 1000) % 10;
        this.param3Mode = (opcode / 10000) % 10;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getParam1Mode() {
        return param1Mode;
    }

    public int getParam2Mode() {
        return param2Mode;
    }

    public int getParam3Mode() {
        return param3Mode;
    }

    public boolean isValid() {
        return (opCode == OP_HALT || (opCode >= OP_ADD && opCode <= OP_EQUALS)) &&
                (param1Mode == MODE_POSITION || param1Mode == MODE_IMMEDIATE) &&
                (param2Mode == MODE_POSITION || param2Mode == MODE_IMMEDIATE) &&
                (param3Mode == MODE_POSITION || param3Mode == MODE_IMMEDIATE);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(opCode);
        switch(opCode) {
            case OP_ADD: buf.append("(ADD)"); break;
            case OP_MULT: buf.append("(MUL)"); break;
            case OP_IN: buf.append("(IN)"); break;
            case OP_OUT: buf.append("(OUT)"); break;
            case OP_JUMP_IF_TRUE: buf.append("(JMP1)"); break;
            case OP_JUMP_IF_FALSE: buf.append("(JMP0)"); break;
            case OP_LESS_THAN: buf.append("(<)"); break;
            case OP_EQUALS: buf.append("(==)"); break;
            case OP_HALT: buf.append("(HALT)"); break;
        }
        return buf.toString();
    }
}
