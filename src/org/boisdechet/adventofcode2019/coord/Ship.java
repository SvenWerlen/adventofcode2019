package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used for Day 15
 * It will try all combination until shortest path is found
 */
public class Ship {

    private Map<Point, RobotDistance> map;

    public static class RobotDistance {
        public Robot robot;
        public Point position;
        public int distance;
        public RobotDistance(Robot robot, Point position, int distance) {
            this.robot = robot.clone();
            this.position = position.clone();
            this.distance = distance;
        }
    }

    public Ship(Robot sourceRobot) {
        Point source = new Point(0,0);
        map = new HashMap<Point, RobotDistance>();
        map.put(source, new RobotDistance(sourceRobot, source, 0));
    }

    private boolean tryMove(Robot rb, Point dest, int curDistance, int direction) {
        Log.d(String.format("Next move %s for distance %d", dest, curDistance));
        // wall => don't even try
        if(map.containsKey(dest) && map.get(dest).distance < 0) {
            return false;
        }
        // distance would be bigger than using another path => don't even try
        else if(map.containsKey(dest) && map.get(dest).distance <= curDistance) {
            return false;
        }
        // try moving robot (disable debugging for that part)
        Robot r = rb.clone();
        boolean debugEnabled = Log.DEBUG; Log.DEBUG = false;
        long status = r.move(direction);
        Log.DEBUG = debugEnabled;
        Log.d(String.format("Move status is %s", status == Robot.STATUS_GOAL ? "GOAL" : status == Robot.STATUS_MOVE ? "MOVE" : "WALL"));
        // oxygen found!
        if(status == Robot.STATUS_GOAL) {
            map.put(dest, new RobotDistance(r, dest, Integer.MAX_VALUE));
            return true;
        }
        else if(status == Robot.STATUS_WALL) {
            if(map.containsKey(dest)) {
                throw new IllegalStateException(String.format("Invalid destination %s as wall!", dest));
            }
            map.put(dest, new RobotDistance(r, dest, -1));
        } else if(status == Robot.STATUS_MOVE) {
            map.put(dest, new RobotDistance(r, dest, curDistance+1));
        }
        return false; // goal not yet found
    }

    public int searchOxygen() {
        int curDistance = 0;
        while(true) {
            // look for all position with current distance
            List<RobotDistance> list = new ArrayList<>();
            for(RobotDistance rb : map.values()) {
                if(rb.distance == curDistance) {
                    list.add(rb);
                }
            }
            // identify all directions for which distance would be less than existing found path
            for(RobotDistance rb : list) {
                if( tryMove(rb.robot, new Point(rb.position.x, rb.position.y -1), curDistance, Robot.MOVE_NORTH)
                    || tryMove(rb.robot, new Point(rb.position.x, rb.position.y +1), curDistance, Robot.MOVE_SOUTH)
                    || tryMove(rb.robot, new Point(rb.position.x +1, rb.position.y), curDistance, Robot.MOVE_EAST)
                    || tryMove(rb.robot, new Point(rb.position.x -1, rb.position.y), curDistance, Robot.MOVE_WEST)
                ) {
                    Log.d(toString());
                    return curDistance+1;
                }
            }
            curDistance++;
        }
    }

    public int fillOxygen() {
        int curDistance = 0;
        while(true) {
            // look for all position with current distance
            List<RobotDistance> list = new ArrayList<>();
            for(RobotDistance rb : map.values()) {
                if(rb.distance == curDistance) {
                    list.add(rb);
                }
            }
            if(list.size() == 0) {
                Log.d(toString());
                return computeFillOxygenDelay();
            }
            // identify all directions for which distance would be less than existing found path
            for(RobotDistance rb : list) {
                tryMove(rb.robot, new Point(rb.position.x, rb.position.y -1), curDistance, Robot.MOVE_NORTH);
                tryMove(rb.robot, new Point(rb.position.x, rb.position.y +1), curDistance, Robot.MOVE_SOUTH);
                tryMove(rb.robot, new Point(rb.position.x +1, rb.position.y), curDistance, Robot.MOVE_EAST);
                tryMove(rb.robot, new Point(rb.position.x -1, rb.position.y), curDistance, Robot.MOVE_WEST);
            }
            curDistance++;
        }
    }

    private int computeFillOxygenDelay() {
        // build new map
        String[] lines = dumpMap().split("\n");
        int[][] grid = new int[lines.length][lines[0].length()];
        for(int y=0; y<lines.length; y++) {
            grid[y] = new int[lines[0].length()];
        }
        {
            int y = 0;
            for(String line : lines) {
                int x = 0;
                for(char c : line.toCharArray()) {
                    switch(c) {
                        case '#':
                        case ' ': grid[y][x] = -1; break;
                        case 'o': grid[y][x] = 0; break;
                        case '.': grid[y][x] = Integer.MAX_VALUE; break;
                        default: throw new IllegalStateException(String.format("Invalid character %s", c));
                    }
                    x++;
                }
                y++;
            }
        }
        // iterate on map from oxygen
        int curDistance = 0;
        while(true) {
            Log.d("Current distance = " + curDistance);
            boolean changed = false;
            for(int y=0; y<grid[0].length; y++) {
                for(int x=0; x<grid.length; x++) {
                    if(grid[y][x] == curDistance) {
                        if(grid[y-1][x] > curDistance+1) { grid[y-1][x]=curDistance+1; } // north
                        if(grid[y+1][x] > curDistance+1) { grid[y+1][x]=curDistance+1; } // south
                        if(grid[y][x+1] > curDistance+1) { grid[y][x+1]=curDistance+1; } // east
                        if(grid[y][x-1] > curDistance+1) { grid[y][x-1]=curDistance+1; } // west
                        changed = true;
                    }
                }
            }
            if(!changed) {
                return curDistance-1;
            }
            Log.d(dumpMap(grid));

            curDistance++;
        }
    }

    private String dumpMap(int[][] grid) {
        StringBuffer buf = new StringBuffer();
        for(int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                buf.append(grid[y][x] < 0 ? '#' : grid[y][x] == Integer.MAX_VALUE ? '.' : 'o');
            }
            buf.append('\n');
        }
        return buf.toString();
    }

    private String dumpMap() {
        StringBuffer buf = new StringBuffer();
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        for(RobotDistance d : map.values()) {
            if(d.position.x < minX) { minX = d.position.x; }
            if(d.position.y < minY) { minY = d.position.y; }
            if(d.position.x > maxX) { maxX = d.position.x; }
            if(d.position.y > maxY) { maxY = d.position.y; }
        }
        for(int y = minY; y <= maxY; y++) {
            for(int x = minX; x <= maxX; x++) {
                Point p = new Point(x,y);
                if(map.containsKey(p)) {
                    RobotDistance d = map.get(p);
                    buf.append(d.distance < 0 ? '#' : d.distance == Integer.MAX_VALUE ? 'o' : '.');
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
        return "Ship:\n" + dumpMap();
    }
}
