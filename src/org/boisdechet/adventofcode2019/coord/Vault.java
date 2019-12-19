package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.Dijkstra;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.boisdechet.adventofcode2019.utils.InputUtil;
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

    private Map<Integer, Key> keys;

    public static class Result {
        List<Key> path = new ArrayList<>();
        int distance = 0;
    }

    public static class Key implements INodeObject {
        int id;
        Point position;
        Map<Integer, Integer> distances;
        Map<Integer, Set<Integer>> requires;

        public char getId() {
            return (char)(id+'a');
        }

        public Key(int id, Point position) {
            this.id = id;
            this.position = position;
            this.distances = new HashMap<>();
            this.requires = new HashMap<>();
        }

        @Override
        public boolean pathExists(Node curNode, Node obj) {
            Key dest = (Key)obj.getObject();
            Log.d(String.format("Checking dependencies between %s and %s", curNode, obj));
            if(!dest.distances.containsKey(id)) {
                return false;
            }
            // check if dependencies in path
            for(Integer dep : dest.requires.get(id)) {
                Log.d(String.format("- Dependency %s", (char)(dep+'a')));
                boolean reqMeet = false;
                Node cur = curNode;
                while(cur != null) {
                    Key pathEl = (Key)cur.getObject();
                    Log.d(String.format("-> Path %s", pathEl.getId()));
                    if(pathEl.id == dep) {
                        reqMeet = true;
                        break;
                    }
                    cur = cur.getPreviousNode();
                }
                if(!reqMeet) {
                    Log.d(String.format("Dependency %s not meet!", (char)(dep+'a')));
                    return false;
                } else {
                    Log.d(String.format("Dependency %s meet!", (char)(dep+'a')));
                }
            }
            return true;
        }

        @Override
        public int getDistanceTo(INodeObject obj) {
            return distances.get(((Key)obj).id);
        }

        @Override
        public String toString() {
            return "" + getId();
        }

        @Override
        public int hashCode() {
            return getId();
        }

        public String toFullString() {
            StringBuffer buf = new StringBuffer();
            List<Integer> ids = new ArrayList<>(distances.keySet());
            Collections.sort(ids);
            for(Integer id : ids) {
                buf.append((char)(id+'a')).append('(');
                buf.append(distances.getOrDefault(id, 0));
                buf.append(":").append(dumpKeys(requires.getOrDefault(id, new HashSet<>()))).append("), ");
            }
            return String.format("%s %s with distances to %s", (char)(id+'a'), position.toString(), buf.toString());
        }

        public static String dumpKeys(Set<Integer> keys) {
            StringBuffer buf = new StringBuffer();
            for(int k : keys) {
                buf.append((char)(k+'A'));
            }
            return buf.toString();
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
                            int keyId = c-'a';
                            keys.put(keyId, new Key(keyId, new Point(x,y)));
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
        Key source = new Key((int)'@'-'a', start);
        fillDistances(source);
        keys.put(source.id, source);
    }

    private void fillDistances(Key curkey) {
        for(Key k : keys.values()) {
            if(k.id == curkey.id || k.distances.containsKey(curkey.id)) {
                continue;
            }
            Set<Integer> deps = new HashSet<>();
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

    private int getDistance(Point src, Point dest, Set<Integer> deps) {
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
                                    deps.add(map[p.y][p.x] - TYPE_DOOR);
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
     * Brute-force approach
     */
    public int minStepsToCollectAllKeys() {
        Set<Node> list = new HashSet<>();
        for(Key k : keys.values()) {
            if(k.getId() != '@') {
                list.add(new Node(k));
            }
        }
        Node start = new Node(keys.get(((int)'@'-'a')));
        start.setDistance(0);
        Node best = minStepsToCollectAllKeys(start, list, Integer.MAX_VALUE);
        return best.getDistance();
    }

    public Node minStepsToCollectAllKeys(Node from, Set<Node> remaining, int bestDistance) {
        //Log.i(String.format("Step %s (%d) with %d remaining and best distance %s", from.toString(), from.getDistance(), remaining.size(), bestDistance == Integer.MAX_VALUE ? "-" : String.valueOf(bestDistance)));
        if(from.getDistance() >= bestDistance) {
            return null;
        }
        else if(remaining.isEmpty()) {
            Log.i(String.format("Best distance found is %d", from.getDistance()));
            return from;
        }
        // prepare all nodes
        Map<Integer, Node> nodesByDistance = new HashMap<>();
        for(Node n : remaining) {
            // explore that path
            if(from.getObject().pathExists(from, n)) {
                nodesByDistance.put(from.getObject().getDistanceTo(n.getObject()), n);
            }
        }
        // try each by trying the one with minimal distance first
        List<Integer> distances = new ArrayList<>(nodesByDistance.keySet());
        Collections.sort(distances);
        Node bestNode = null;
        for(Integer d : distances) {
            Node node = nodesByDistance.get(d);
            Node next = new Node(node.getObject());
            next.setDistance(from.getDistance() + d);
            next.setPreviousNode(from);
            Set<Node> nextRemaining = new HashSet<>(remaining);
            nextRemaining.remove(node);
            Node best = minStepsToCollectAllKeys(next, nextRemaining, bestDistance);
            if(best != null && best.getDistance() < bestDistance) {
                bestNode = best;
                bestDistance = best.getDistance();
            }
        }

        if(bestNode != null) {
            Log.i(String.format("No other best distance than %d", bestNode.getDistance()));
        }
        return bestNode;
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
        List<Integer> ids = new ArrayList<>(keys.keySet());
        Collections.sort(ids);
        for(Integer id : ids) {
            buf.append(keys.get(id).toFullString()).append('\n');
        }
        buf.append('\n');
        return buf.toString();
    }
}
