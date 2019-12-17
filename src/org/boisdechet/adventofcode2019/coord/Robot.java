package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;

public class Robot implements Cloneable {

    public static final int MOVE_NORTH = 1;
    public static final int MOVE_SOUTH = 2;
    public static final int MOVE_WEST  = 3;
    public static final int MOVE_EAST  = 4;

    public static final int STATUS_WALL = 0;
    public static final int STATUS_MOVE = 1;
    public static final int STATUS_GOAL = 2;

    private OpCodeMachine machine;

    private Robot() {}

    public Robot(long[] instructions) {
        this.machine = new OpCodeMachine(instructions);
    }

    public long move(int direction) { return this.machine.execute(direction); }

    @Override
    protected Robot clone() {
        Robot rob = new Robot();
        rob.machine = this.machine.clone();
        return rob;
    }
}
