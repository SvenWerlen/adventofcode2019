package org.boisdechet.adventofcode2019.coord;

import jdk.jshell.spi.ExecutionControl;
import org.boisdechet.adventofcode2019.dijstra.DijkstraDynamic;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.*;


public class Vault {

    public static final int TYPE_START = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_WALL  = 2;
    public static final int TYPE_DOOR  = 100;
    public static final int TYPE_KEY   = 200;

    private Point start;
    private int[][] map;

    private Map<Character, Key> keys;

    public static class Result {
        List<Key> path = new ArrayList<>();
        int distance = 0;
    }

    public static class Key implements INodeObject {
        Character id;
        Point position;
        Map<Character, Integer> distances;
        Map<Character, Set<Character>> requires;

        public char getId() {
            return id;
        }

        public Key(Character id, Point position) {
            this.id = id;
            this.position = position;
            this.distances = new HashMap<>();
            this.requires = new HashMap<>();
        }

        @Override
        public String getUniqueId() {
            return "" + id;
        }

        @Override
        public boolean pathExists(INodeObject obj) {
            throw new IllegalStateException("Not implemented! Don't use simple Dijkstra with Keys");
        }

        @Override
        public int getDistanceTo(INodeObject obj) {
            return distances.get(((Key)obj).id);
        }

        @Override
        public String toString() {
            return getUniqueId();
        }

        public String toFullString() {
            StringBuffer buf = new StringBuffer();
            List<Character> ids = new ArrayList<>(distances.keySet());
            Collections.sort(ids);
            for(Character id : ids) {
                buf.append(id).append('(');
                buf.append(distances.getOrDefault(id, 0));
                buf.append(":").append(dumpKeys(requires.getOrDefault(id, new HashSet<>()))).append("), ");
            }
            return String.format("%s %s with distances to %s", id, position.toString(), buf.toString());
        }

        public static String dumpKeys(Set<Character> keys) {
            StringBuffer buf = new StringBuffer();
            for(Character k : keys) {
                buf.append(k);
            }
            return buf.toString();
        }
    }

    public static class VaultDijkstraController implements DijkstraDynamic.Controller {

        private List<Key> keys;

        private VaultDijkstraController(List<Key> keys) {
            this.keys = keys;
        }

        @Override
        public List<INodeObject> getConnectedNodes(Node from) {
            // build available keys
            Map<Character, Key> hasKeys = new HashMap<>();
            Node cur = from;
            while(cur != null) {
                Key k = (Key)cur.getObject();
                hasKeys.put(k.getId(), k);
                cur = cur.getPreviousNode();
            }

            // build connected nodes
            List<INodeObject> result = new ArrayList<>();
            Key curKey = (Key)from.getObject();
            for(Key k : keys) {
                if(!hasKeys.containsKey(k.getId())) {
                    // check dependencies
                    boolean depOK = true;
                    for(Character dep : curKey.requires.getOrDefault(k.id, new HashSet<>())) {
                        if(!hasKeys.containsKey(dep)) {
                            depOK = false; break;
                        }
                    }
                    if(depOK) {
                        result.add(k);
                    }
                }
            }
            return result;
        }

        @Override
        public int getDistance(Node from, INodeObject to) {
            return from.getObject().getDistanceTo(to);
        }

        @Override
        public boolean targetReached(Node target) {
            int count = 1;
            Node curNode = target;
            while((curNode = curNode.getPreviousNode()) != null) {
                count++;
            }
            return count == keys.size();
        }
    }

    public Vault(String mapAsString) {
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
                            keys.put(c, new Key(c, new Point(x,y)));
                            map[y][x] = TYPE_KEY + (c-'a');
                        } else {
                            throw new IllegalStateException(String.format("Illegal map character %s", c));
                        }
                }
                x++;
            }
            y++;
        }
        // computes all distances from a key to another (and from source)
        for(Key k : keys.values()) {
            fillDistances(k);
        }
        Key source = new Key('@', start);
        fillDistances(source);
        keys.put(source.id, source);
    }

    private void fillDistances(Key curkey) {
        for(Key k : keys.values()) {
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

    /**
     * Dijkstra
     */
    public int minStepsToCollectAllKeys() {
        DijkstraDynamic.Controller controller = new Vault.VaultDijkstraController(new ArrayList<>(keys.values()));
        Node end = new DijkstraDynamic(controller).getShortestPath(keys.get('@'));
        return end.getDistance();
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
