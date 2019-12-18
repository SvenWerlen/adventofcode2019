package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ScaffoldingMap {

    private static final int DIRECTION_NORTH = 0;
    private static final int DIRECTION_EAST  = 1;
    private static final int DIRECTION_SOUTH = 2;
    private static final int DIRECTION_WEST  = 3;
    private static final String[] DIRECTION_NAMES = { "NORTH", "EAST", "SOUTH", "WEST"};

    private static final int TYPE_SCAFF = '#';
    private static final int TYPE_ROBOT = '^';
    private static final int TYPE_VISITED = 'X';
    private static final int TYPE_CROSS = 'O';

    private int maxX;
    private int maxY;
    private long output;
    private Map<Point, Integer> map;

    public static class Step {
        public boolean turnLeft;
        public int stepCount;

        public Step(int stepCount, boolean turnLeft) {
            this.stepCount = stepCount;
            this.turnLeft = turnLeft;
        }

        @Override
        public String toString() {
            return String.format("%s%d", turnLeft ? "L" : "R", stepCount);
        }
    }

    public ScaffoldingMap(long[] instructions) {
        this(instructions, null);
    }

    public ScaffoldingMap(long[] instructions, String input) {
        map = new HashMap<>();
        OpCodeMachine m;
        if(input == null) {
            m = new OpCodeMachine(instructions);
        } else {
            int[] inputs = new int[input.length()];
            for(int i=0; i<input.length(); i++) {
                inputs[i] = (int)input.charAt(i);
            }
            m = new OpCodeMachine(instructions, inputs);
        }
        long code;
        int x = 0; int y = 0;

        while ((code = m.execute(0)) != OpCodeMachine.HALT) {
            if (code != 46 && code != 10) {
                map.put(new Point(x, y), Math.toIntExact(code));
            }
            x++;
            if (code == 10) {
                y++;
                maxX = Math.max(maxX, x);
                x = 0;
            }
            if (code > 255) {
                output = code;
            }
        }
        maxY = y;
    }

    public long getOutput() {
        return output;
    }

    private static boolean isScaff(Map<Point, Integer> map, Point point, boolean unvisitedOnly) {
        if(!map.containsKey(point)) {
            return false;
        }
        int type = map.get(point);
        return type == TYPE_SCAFF || (type == TYPE_VISITED && !unvisitedOnly);
    }

    private List<Point> followPath(Point start, Map<Point, Integer> map, int direction) {
        Log.d(String.format("Following path from %s in direction %s", start, DIRECTION_NAMES[direction]));
        List<Point> path = new ArrayList<>();
        while(true) {
            // find next position
            Point nextPos;
            switch(direction) {
                case DIRECTION_NORTH: nextPos = new Point(start.x, start.y-1); break;
                case DIRECTION_SOUTH: nextPos = new Point(start.x, start.y+1); break;
                case DIRECTION_EAST:  nextPos = new Point(start.x+1, start.y); break;
                case DIRECTION_WEST:  nextPos = new Point(start.x-1, start.y); break;
                default: throw new IllegalStateException(String.format("Illegal direction %d", direction));
            }
            // check if valid
            if(isScaff(map, nextPos, false)) {
                path.add(start);
                if(map.get(nextPos) == TYPE_VISITED) {
                    map.put(nextPos, TYPE_CROSS);
                } else {
                    map.put(nextPos, TYPE_VISITED);
                }
                start = nextPos;
                continue;
            }
            break;
        }
        // try all other combinations
        List<Point> path1;
        List<Point> path2;
        if(direction == DIRECTION_NORTH || direction == DIRECTION_SOUTH) {
            path1 = isScaff(map, new Point(start.x-1, start.y), true) ? followPath(start, map, DIRECTION_WEST) : new ArrayList<>();
            path2 = isScaff(map, new Point(start.x+1, start.y), true) ? followPath(start, map, DIRECTION_EAST) : new ArrayList<>();
        } else {
            path1 = isScaff(map, new Point(start.x, start.y+1), true) ? followPath(start, map, DIRECTION_SOUTH) : new ArrayList<>();
            path2 = isScaff(map, new Point(start.x, start.y-1), true) ? followPath(start, map, DIRECTION_NORTH) : new ArrayList<>();
        }
        // dead end
        if(path1.size() >= path2.size()) {
            path.addAll(path1);
        } else {
            path.addAll(path2);
        }
        return path;
    }

    public List<Point> buildPath() {
        // find start
        Point start = null;
        for(Point p : map.keySet()) {
            if(map.get(p) == TYPE_ROBOT) {
                start = p;
                break;
            }
        }
        Log.d(String.format("Starting point is %s", start));
        List<Point> path = followPath(start, map, DIRECTION_NORTH);
        Log.d(String.format("End point is %s", path.get(path.size()-1)));
        Log.d(dumpGrid(map));
        return path;
    }

    public static List<Step> stepsFromPath(List<Point> path) {
        List<Step> steps = new ArrayList<>();
        Point prevPt = null;
        int curDir = DIRECTION_NORTH;
        int stepCount = 1;
        boolean prevTurnLeft = false;
        for(Point p : path) {
            // first point
            if(prevPt == null) {
                prevPt = p;
                continue;
            }

            // continue
            if((curDir == DIRECTION_NORTH && p.y == prevPt.y-1) ||
                    (curDir == DIRECTION_SOUTH && p.y == prevPt.y+1) ||
                    (curDir == DIRECTION_EAST && p.x == prevPt.x+1) ||
                    (curDir == DIRECTION_WEST && p.x == prevPt.x-1)) {
                stepCount++;
            }
            // turn
            else {
                if(stepCount > 1) {
                    steps.add(new Step(stepCount, prevTurnLeft));
                }
                switch(curDir) {
                    case DIRECTION_NORTH:
                        if(p.x == prevPt.x-1) {
                            curDir = DIRECTION_WEST;
                            prevTurnLeft = true;
                        } else {
                            curDir = DIRECTION_EAST;
                            prevTurnLeft = false;
                        }
                        break;
                    case DIRECTION_SOUTH:
                        if(p.x == prevPt.x+1) {
                            curDir = DIRECTION_EAST;
                            prevTurnLeft = true;
                        } else {
                            curDir = DIRECTION_WEST;
                            prevTurnLeft = false;
                        }
                        break;
                    case DIRECTION_EAST:
                        if(p.y == prevPt.y+1) {
                            curDir = DIRECTION_SOUTH;
                            prevTurnLeft = false;
                        } else {
                            curDir = DIRECTION_NORTH;
                            prevTurnLeft = true;
                        }
                        break;
                    case DIRECTION_WEST:
                        if(p.y == prevPt.y-1) {
                            curDir = DIRECTION_NORTH;
                            prevTurnLeft = false;
                        } else {
                            curDir = DIRECTION_SOUTH;
                            prevTurnLeft = true;
                        }
                        break;
                }

                stepCount = 1;
            }
            prevPt = p;
        }
        steps.add(new Step(stepCount, prevTurnLeft));
        return steps;
    }

    public int alignParametersSum() {
        int sum = 0;
        for(Point pt : map.keySet()) {
            int type = map.get(pt);
            if( type == TYPE_CROSS) {
                sum += pt.x*pt.y;
            }
        }
        return sum;
    }

    private String dumpGrid(Map<Point, Integer> map) {
        StringBuffer buf = new StringBuffer();
        for(int y = 0; y<=maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                Point point = new Point(x,y);
                if(map.containsKey(point)) {
                    buf.append((char)map.get(point).intValue());
                } else {
                    buf.append(' ');
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        return dumpGrid(this.map);
    }
}
