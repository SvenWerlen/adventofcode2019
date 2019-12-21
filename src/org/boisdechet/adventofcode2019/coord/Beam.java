package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.Log;

public class Beam {

    public static final int STATUS_OUTSIDE = 1;
    public static final int STATUS_INSIDE = 2;
    public static final int STATUS_PERFECT = 3;

    long[] instr;

    public Beam(long[] instr) {
        this.instr = instr;
    }

    public boolean isPulled(int x, int y) {
        boolean debug = Log.DEBUG;
        Log.DEBUG = false;
        boolean status = new OpCodeMachine(instr, new int[] {x,y}).execute(0) == 1;
        Log.DEBUG = debug;
        //if(Log.DEBUG) { Log.d(String.format("Status of (%d,%d) is %s", x,y, status ? "PULLED": "-"));}
        return status;
    }

    /**
     * See
     * http://villemin.gerard.free.fr/GeomLAV/Triangle/Particul/Carretrg.htm
     */
    public static float computeSquareSize(int h, int a) {
        return ((float)h * a) / (h + a);
    }

    private int stepsHorizontal(int x, int y, int steps, boolean expStatus) {
        int curX = x;
        while(isPulled(curX, y) == expStatus) {
            curX += steps;
        }
        return curX - steps;
    }

    private int stepsVertical(int x, int y, int steps, boolean expStatus) {
        int curY = y;
        while(isPulled(x, curY) == expStatus) {
            curY += steps;
        }
        return curY - steps;
    }

    private int checkPositionFromBottomLeft(int x, int y, int squareSize) {
        // top right corner
        x = x+squareSize-1;
        y = y-squareSize+1;
        // outside
        if(!isPulled(x, y)) {
            return STATUS_OUTSIDE;
        } else {
            if(!isPulled(x+1, y) && !isPulled(x, y-1)) {
                return STATUS_PERFECT;
            } else {
                return STATUS_INSIDE;
            }
        }
    }

    private int checkPositionFromTopRight(int x, int y, int squareSize) {
        // top right corner
        x = x-squareSize+1;
        y = y+squareSize-1;
        // outside
        if(!isPulled(x, y)) {
            return STATUS_OUTSIDE;
        } else {
            if(!isPulled(x-1, y) && !isPulled(x, y+1)) {
                return STATUS_PERFECT;
            } else {
                return STATUS_INSIDE;
            }
        }
    }

    private Point findPerfectCorner(int fromX, int fromY, int squareSize) {
        int x = fromX;
        int y = fromY;
        int status =  checkPositionFromBottomLeft(x, y, squareSize);
        Point lastPos = new Point(x,y);
        while(status == STATUS_INSIDE || status == STATUS_PERFECT) {
            if(Log.DEBUG) { Log.d(String.format("Perfect corner is not (%d,%d) status %s", x,y, status == STATUS_OUTSIDE ? "outside" : "inside")); }
            lastPos = new Point(x, y);
            x -= 5;
            y--;
            x = stepsHorizontal(x, y, 1, false)+1;
            status = checkPositionFromBottomLeft(x, y, squareSize);
        }
        Log.d(String.format("Perfect bottom left corner is %s", lastPos));
        lastPos.y -= squareSize-1;
        int lastX = stepsHorizontal(lastPos.x, lastPos.y, 1, true);
        Log.d(String.format("Size is %d", lastX - lastPos.x + 1));
        return lastPos;
    }

    public Point findSquare(int squareSize, int initialGuessX, int initialGuessY) {
        int curX = initialGuessX;
        int curY = initialGuessY;
        int steps = 1;
        curX=stepsHorizontal(curX, curY, steps, false)+1;
        int previous = curX;
        // initial position found!
        while(true) {
            if(Log.DEBUG) { Log.i(String.format("Bottom left position (%d,%d) with steps %d", curX, curY, steps)); }
            curX=stepsHorizontal(curX, curY, steps, true);
            if(checkPositionFromTopRight(curX, curY, squareSize) != STATUS_OUTSIDE) {
                curX=stepsHorizontal(curX, curY, 1, true) - squareSize;
                curY=stepsVertical(curX, curY+squareSize, 1,true);
                return findPerfectCorner(curX, curY, squareSize);
            }
            steps = (curX-previous)/5;
            //if(Log.DEBUG) { Log.d("\n" + dump(curX-20, curY, 25)); }
            previous = curY;
            if(Log.DEBUG) { Log.i(String.format("Top right position (%d,%d) with steps %d", curX, curY, steps)); }
            // bottom left
            curY=stepsVertical(curX, curY, steps, true);
            steps = (curY-previous)/5;
            if(checkPositionFromBottomLeft(curX, curY, squareSize) != STATUS_OUTSIDE) {
                curY=stepsVertical(curX, curY, 1, true);
                return findPerfectCorner(curX, curY-1, squareSize);
            }
            previous = curX;
        }
    }

    public String dump(int fromX, int fromY, int square) {
        StringBuffer buf = new StringBuffer();
        for(int y = fromY; y < fromY+square; y++) {
            for(int x = fromX; x < fromX+square; x++) {
                buf.append(isPulled(x,y) ? '#' : ' ');
            }
            buf.append('\n');
        }
        return buf.toString();
    }

}
