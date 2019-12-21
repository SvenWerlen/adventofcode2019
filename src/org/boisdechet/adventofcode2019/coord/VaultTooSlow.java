package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.*;

/**
 * First attempt of vault (too slow!!)
 * Brute-forcing is not a valid approach for sample 3 and puzzle
 */
public class VaultTooSlow {

    public static final int TYPE_START = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_WALL  = 2;
    public static final int TYPE_DOOR  = 100;
    public static final int TYPE_KEY   = 200;

    private Point start;
    private int[][] map;
    private int keyCount;
    private HashMap<String, Integer> distances;

    public VaultTooSlow(String mapAsString) {
        keyCount = 0;
        String lines[] = mapAsString.split("\n");
        map = new int[lines.length][lines[0].length()];
        int y = 0;
        for(String line : lines) {
            int x = 0;
            for(char c : line.toCharArray()) {
                switch(c) {
                    case '@': map[y][x] = TYPE_START; start = new Point(x,y); break;
                    case '#': map[y][x] = TYPE_WALL; break;
                    case '.': map[y][x] = TYPE_EMPTY; break;
                    default:
                        if(c >= 'A' && c <= 'Z') {
                            map[y][x] = TYPE_DOOR + (c-'A');
                        } else if(c >= 'a' && c <= 'z') {
                            map[y][x] = TYPE_KEY + (c-'a');
                            keyCount++;
                        } else {
                            throw new IllegalStateException(String.format("Illegal map character %s", c));
                        }
                }
                x++;
            }
            y++;
        }
    }

    /**
     * Returns >= 0 if key found at position
     * Returns -1 if valid step
     * Returns -100 if invalid step
     */
    private int checkPosition(int[][] minDist, Set<Integer> keys, int x, int y, int curDist) {
        if(map[y][x] == TYPE_WALL || minDist[y][x] <= curDist) {
            return -100;
        }
        // open door (if key available)
        else if(map[y][x] >= TYPE_DOOR && map[y][x] <= TYPE_DOOR + 'Z' && !keys.contains(map[y][x]-TYPE_DOOR)) {
            if(Log.DEBUG) { Log.d(String.format("Door %s cannot be opened", (char)('A'+map[y][x]-TYPE_DOOR))); }
            return -100;
        }

        minDist[y][x] = curDist+1;
        // collect key (ignore if key already collected)
        if(map[y][x] >= TYPE_KEY && map[y][x] <= TYPE_KEY + 'z' && !keys.contains(map[y][x]-TYPE_KEY)) {
            return map[y][x] - TYPE_KEY;
        }
        return -1; // valid step
    }

    public int minStepsToCollectAllKeys() {
        distances = new HashMap<>();
        return minStepsToCollectAllKeys(new HashSet<>(), this.start, 0, Integer.MAX_VALUE);
    }

    public String generateKey(Point point, Set<Integer> keys) {
        List<Integer> list = new ArrayList<>(keys);
        Collections.sort(list);
        return point.toString() + InputUtil.convertToString(keys.toArray());
    }

    public int minStepsToCollectAllKeys(Set<Integer> keys, Point start, int curDistance, int bestDistance) {
        // check if same position has been visited in the past
        String key = generateKey(start, keys);
        if(this.distances.containsKey(key) && this.distances.get(key) <= curDistance) {
            Log.i(String.format("%s already visited with %d distance (rather than %d)", key, this.distances.get(key), curDistance));
            return Integer.MAX_VALUE;
        }
        // all keys found
        if(keys.size() == keyCount) {
            if(Log.DEBUG) { Log.d("Best distance found = " + curDistance); }
            return curDistance;
        }
        // initialize minDist map
        int[][] minDist = new int[map.length][map[0].length];
        for(int y = 0; y < map.length-0; y++) { Arrays.fill(minDist[y], Integer.MAX_VALUE); }
        // initialize start point
        minDist[start.y][start.x] = 0;

        if(Log.DEBUG) { Log.d(String.format("Current distance %d with start %s", curDistance, start)); }
        if(Log.DEBUG) { Log.d(dumpMinDistMap(minDist, keys, start)); }


        int steps = 1;
        while(true) {
            boolean inProgress = false;
            for(int y = 1; y < map.length-1; y++) {
                for (int x = 1; x < map[0].length-1; x++) {
                    if (minDist[y][x] == steps-1) {
                        // next steps
                        int code1 = checkPosition(minDist, keys, x, y-1, steps-1); // NORTH
                        int code2 = checkPosition(minDist, keys, x, y+1, steps-1); // SOUTH
                        int code3 = checkPosition(minDist, keys, x+1, y, steps-1); // EAST
                        int code4 = checkPosition(minDist, keys, x-1, y, steps-1); // WEST
                        // no valid position (ugly check)
                        if(code1+code2+code3+code4 > -400) { inProgress = true; }
                        // key collected?
                        if(code1 >= 0) {
                            Set<Integer> newKeys = new LinkedHashSet<>(keys);
                            newKeys.add(code1);
                            bestDistance = Math.min(bestDistance, minStepsToCollectAllKeys(newKeys, new Point(x,y-1), steps + curDistance, bestDistance));
                        }
                        if(code2 >= 0) {
                            Set<Integer> newKeys = new LinkedHashSet<>(keys);
                            newKeys.add(code2);
                            bestDistance = Math.min(bestDistance, minStepsToCollectAllKeys(newKeys, new Point(x,y+1), steps + curDistance, bestDistance));
                        }
                        if(code3 >= 0) {
                            Set<Integer> newKeys = new LinkedHashSet<>(keys);
                            newKeys.add(code3);
                            bestDistance = Math.min(bestDistance, minStepsToCollectAllKeys(newKeys, new Point(x+1,y), steps + curDistance, bestDistance));
                        }
                        if(code4 >= 0) {
                            Set<Integer> newKeys = new LinkedHashSet<>(keys);
                            newKeys.add(code4);
                            bestDistance = Math.min(bestDistance, minStepsToCollectAllKeys(newKeys, new Point(x-1,y), steps + curDistance, bestDistance));
                        }
                    }
                }
            }
            if(!inProgress || (curDistance + steps) >= bestDistance) {
                if(Log.DEBUG) { Log.d(String.format("Ignoring that path after %d steps %s", curDistance + steps, !inProgress ? "(no path)" : "")); }
                return bestDistance;
            }
            if(Log.DEBUG) { Log.d("Steps " + steps); }
            if(Log.DEBUG) { Log.d(dumpMinDistMap(minDist, keys, start)); }
            steps++;
        }
    }

    private String dumpMinDistMap(int[][] minDist, Set<Integer> keys, Point startPos) {
        StringBuffer buf = new StringBuffer(minDist.length*minDist[0].length);
        buf.append("Keys = ");
        for(int key : keys) { buf.append((char)(key + 'a')).append(' '); }
        buf.append('\n');

        for(int y=0; y<minDist.length; y++) {
            for(int x=0; x<minDist[0].length; x++) {
                if(this.map[y][x] == TYPE_WALL) {
                    buf.append('#');
                }
                else if(startPos.equals(new Point(x,y))) {
                    buf.append('@');
                }
                else if(minDist[y][x] == Integer.MAX_VALUE) {
                    if(this.map[y][x] >= TYPE_KEY && this.map[y][x] <= TYPE_DOOR + 'z' && !keys.contains(this.map[y][x] - TYPE_KEY)) {
                        buf.append((char)(this.map[y][x] - TYPE_KEY + 'a'));
                    } else if(this.map[y][x] >= TYPE_DOOR && this.map[y][x] <= TYPE_DOOR + 'Z' && !keys.contains(this.map[y][x] - TYPE_DOOR)) {
                        buf.append((char)(this.map[y][x] - TYPE_DOOR + 'A'));
                    } else {
                        buf.append(' ');
                    }
                } else {
                    buf.append(minDist[y][x] % 10);
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer(map.length*map[0].length);
        for(int y=0; y<map.length; y++) {
            for(int x=0; x<map[0].length; x++) {
                switch(map[y][x]) {
                    case TYPE_START: buf.append('@'); break;
                    case TYPE_WALL: buf.append('#'); break;
                    case TYPE_EMPTY: buf.append('.'); break;
                    default:
                        if(map[y][x] >= TYPE_KEY) {
                            buf.append((char)(map[y][x] - TYPE_KEY + 'a')); break;
                        } else if(map[y][x] >= TYPE_DOOR) {
                            buf.append((char)(map[y][x] - TYPE_DOOR + 'A')); break;
                        } else {
                            throw new IllegalStateException(String.format("Illegal map character %s", map[y][x]));
                        }
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
