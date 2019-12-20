package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.DijkstraDynamic;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;

import java.util.*;


public class Vault {

    public static final int TYPE_START = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_WALL  = 2;
    public static final int TYPE_START_2 = 3;
    public static final int TYPE_START_3 = 4;
    public static final int TYPE_START_4 = 5;
    public static final int TYPE_DOOR  = 100;
    public static final int TYPE_KEY   = 200;

    private Point start;
    private Point start2;
    private Point start3;
    private Point start4;
    private int[][] map;
    private Map<Character, VaultKey> keys;

    /**
     * Vault class used for Day 18
     * 1) reads the map
     * 2) pre-computes all distances and pre-requisites
     * 3) run Dijkstra algorithm to find best path
     */
    public Vault(String mapAsString) {
        this(mapAsString, false);
    }

    public Vault(String mapAsString, boolean multivault) {
        // read input
        String lines[] = mapAsString.split("\n");
        map = new int[lines.length][lines[0].length()];
        keys = new HashMap<>();
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
                            keys.put(c, new VaultKey(c, new Point(x,y)));
                            map[y][x] = TYPE_KEY + (c-'a');
                        } else {
                            throw new IllegalStateException(String.format("Illegal map character %s", c));
                        }
                }
                x++;
            }
            y++;
        }
        if(multivault) {
            map[start.y-1][start.x-1]=TYPE_START;
            map[start.y-1][start.x]=TYPE_WALL;
            map[start.y-1][start.x+1]=TYPE_START_2;
            map[start.y][start.x-1]=TYPE_WALL;
            map[start.y][start.x]=TYPE_WALL;
            map[start.y][start.x+1]=TYPE_WALL;
            map[start.y+1][start.x-1]=TYPE_START_3;
            map[start.y+1][start.x]=TYPE_WALL;
            map[start.y+1][start.x+1]=TYPE_START_4;
            start2 = new Point(start.x+1, start.y-1);
            start3 = new Point(start.x-1, start.y+1);
            start4 = new Point(start.x+1, start.y+1);
            start = new Point(start.x-1, start.y-1);
            keys.put('*', new VaultKey('*', start2));
            keys.put('$', new VaultKey('$', start3));
            keys.put('^', new VaultKey('^', start4));
        }
        keys.put('@', new VaultKey('@', start));
        // computes all distances from a key to another (and from source)
        for(VaultKey k : keys.values()) {
            fillDistances(k);
        }
    }

    private void fillDistances(VaultKey curkey) {
        for(VaultKey k : keys.values()) {
            if(k.id == curkey.id || k.distances.containsKey(curkey.id)) {
                continue;
            }
            Set<Character> deps = new HashSet<>();
            int distance = getDistance(curkey.position, k.position, deps);
            if(distance < Integer.MAX_VALUE) {
                curkey.distances.put(k.id, distance);
                curkey.requires.put(k.id, deps);
                k.distances.put(curkey.id, distance);
                k.requires.put(curkey.id, deps);
            }
        }
    }

    private void fillPath(List<Point> path, int[][] distances, Point to) {
        path.add(to);
        int dist = distances[to.y][to.x];
        if(dist == 1) {
            return;
        }
        if(distances[to.y-1][to.x]==dist-1) { fillPath(path, distances, new Point(to.x,to.y-1)); }
        else if(distances[to.y+1][to.x]==dist-1) { fillPath(path, distances, new Point(to.x,to.y+1)); }
        else if(distances[to.y][to.x-1]==dist-1) { fillPath(path, distances, new Point(to.x-1,to.y)); }
        else if(distances[to.y][to.x+1]==dist-1) { fillPath(path, distances, new Point(to.x+1,to.y)); }
        else { throw new IllegalStateException(String.format("Path couldn't be found for dist %d!", dist)); }
    }

    /**
     * Pre-computes distances and determines dependencies between 2 positions
     */
    private int getDistance(Point src, Point dest, Set<Character> deps) {
        int[][] minDist = new int[map.length][map[0].length];
        for(int y = 0; y < map.length; y++) { Arrays.fill(minDist[y], Integer.MAX_VALUE); }
        minDist[src.y][src.x]=1;
        int steps = 0;
        while(true) {
            steps++;
            boolean inProgress = false;
            for(int y = 1; y < map.length-1; y++) {
                for (int x = 1; x < map[0].length - 1; x++) {
                    if(minDist[y][x]==steps) {
                        if(dest.equals(new Point(x,y))) {
                            // find path
                            List<Point> path = new ArrayList<>();
                            //Log.i(dumpMinDistMap(minDist, new HashSet<>(), src));
                            fillPath(path, minDist, dest);
                            // find dependencies
                            for(Point p : path) {
                                if(map[p.y][p.x] >= TYPE_DOOR && map[p.y][p.x] <= TYPE_DOOR + 'A') {
                                    deps.add((char) ('a' + map[p.y][p.x] - TYPE_DOOR));
                                }
                            }
                            return steps-1;
                        }
                        if(map[y-1][x] != TYPE_WALL && minDist[y-1][x]>steps+1) { minDist[y-1][x] = steps+1; inProgress = true; } // NORTH
                        if(map[y+1][x] != TYPE_WALL && minDist[y+1][x]>steps+1) { minDist[y+1][x] = steps+1; inProgress = true; } // SOUTH
                        if(map[y][x-1] != TYPE_WALL && minDist[y][x-1]>steps+1) { minDist[y][x-1] = steps+1; inProgress = true; } // WEST
                        if(map[y][x+1] != TYPE_WALL && minDist[y][x+1]>steps+1) { minDist[y][x+1] = steps+1; inProgress = true; } // EAST
                    }
                }
            }
            //Log.i(dumpMinDistMap(minDist, new HashSet<>(), src));
            if(!inProgress) {
                return Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Dijkstra
     */
    public int minStepsToCollectAllKeys() {
        return minStepsToCollectAllKeys(true);
    }

    public int minStepsToCollectAllKeys(boolean speedup) {
        if(start2 == null) {
            DijkstraDynamic.Controller controller = new VaultDijkstraController(new ArrayList<>(keys.values()));
            Node end = new DijkstraDynamic(controller).getShortestPath(keys.get('@'));
            return end.getDistance();
        } else {
            DijkstraDynamic.Controller controller = new VaultDijkstraMultiController(new ArrayList<>(keys.values()), speedup);
            Node end = new DijkstraDynamic(controller).getShortestPath(new VaultMultiKey(keys.get('@'), keys.get('*'), keys.get('$'), keys.get('^'), null));
            return end.getDistance();
        }
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer(map.length*map[0].length);
        for(int y=0; y<map.length; y++) {
            for(int x=0; x<map[0].length; x++) {
                switch(map[y][x]) {
                    case TYPE_START:
                    case TYPE_START_2:
                    case TYPE_START_3:
                    case TYPE_START_4:
                        buf.append('@'); break;
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
        buf.append("Keys:\n");
        List<Character> ids = new ArrayList<>(keys.keySet());
        Collections.sort(ids);
        for(Character id : ids) {
            buf.append(keys.get(id).toFullString()).append('\n');
        }
        buf.append('\n');
        return buf.toString();
    }
}
