package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.coord.Point;

public class Robot {

    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;

    private OpCodeMachine prog;
    private int direction;
    private Point position;

    public Robot(OpCodeMachine program) {
        this.prog = program;
        this.direction = 0;
        this.position = new Point(0,0);
    }


    public static class Output {
        public final static int COLOR_BLACK = 0;
        public final static int COLOR_WHITE = 1;
        public final static int DIR_LEFT90  = 0;
        public final static int DIR_RIGHT90 = 1;
        public int color;
        public int direction;

        public Output() {}

        public Output(int color, int direction) {
            this.color = color;
            this.direction = direction;
        }

        public boolean isValid() {
            return (color == COLOR_BLACK || color == COLOR_WHITE) &&
                    (direction == DIR_LEFT90 || direction == DIR_RIGHT90);
        }

        @Override
        public String toString() {
            if(isValid()) {
                return String.format("Output (%s,%s)", color == COLOR_BLACK ? "black" : "white", direction == DIR_LEFT90 ? "left" : "right");
            } else {
                return String.format("Output (%d,%d)", color, direction);
            }
        }

    }

    public Output next(int curColor) {
        Output output = new Output();
        long result1 = prog.execute(curColor, curColor, false);
        if(result1 == OpCodeMachine.HALT) {
            return null;
        }
        long result2 = prog.execute(curColor, curColor, false);
        if(result2 == OpCodeMachine.HALT) {
            return null;
        }
        output.color = Math.toIntExact(result1);
        output.direction = Math.toIntExact(result2);

        if(!output.isValid()) {
            throw new IllegalStateException(String.format("Invalid output: %s", output.toString()));
        }

        return output;
    }

    public void paintAndMove(EmergencyHull hull, Output instructions) {
        // paint
        hull.paint(position, instructions.color);
        // get next direction
        direction = (instructions.direction == Output.DIR_LEFT90 ? direction - 1 : direction + 1) % 4;
        direction = direction >= DIR_NORTH ? direction : DIR_WEST;
        // change position
        switch(direction) {
            case DIR_NORTH: position = new Point(position.x, position.y-1); break;
            case DIR_EAST: position = new Point(position.x+1, position.y); break;
            case DIR_SOUTH: position = new Point(position.x, position.y+1); break;
            case DIR_WEST: position = new Point(position.x-1, position.y); break;
            default: throw new IllegalStateException(String.format("Invalid direction %d", direction));
        }
    }

    public Point getPosition() {
        return new Point(position.x, position.y);
    }

    public int getDirection() {
        return direction;
    }

}
