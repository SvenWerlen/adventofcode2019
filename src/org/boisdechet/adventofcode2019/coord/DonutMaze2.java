package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.Dijkstra;
import org.boisdechet.adventofcode2019.dijstra.DijkstraPoint;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.*;

public class DonutMaze2 {

    private static final char TYPE_OPEN = '.';

    private int maxX, maxY;
    private Map<Point, DijkstraPoint> points;
    private Map<Warp, DijkstraPoint> warps;
    private Map<String, DijkstraPoint> warpsMulti;
    private DijkstraPoint start, end;
    private boolean multidimentional;

    public static class Warp extends Point {
        private char id;
        private String name;
        private Warp connectedTo;
        private boolean outer;
        private int depth;

        public Warp(String name, int x, int y) {
            super(x, y);
            this.name = name;
            this.depth = 0;
        }

        public Warp(Warp copy, int depth) {
            this(copy.name, copy.x, copy.y);
            this.outer = copy.outer;
            this.depth = depth;
        }

        public Warp getConnectedTo() {
            return connectedTo;
        }

        public void setConnectedTo(Warp other) {
            this.connectedTo = other;
        }

        public String toString(int depth) {
            return String.format("%s%d-%s%s", name, depth, outer ? 'O' : 'I', super.toString());
        }

        @Override
        public String toString() {
            return toString(depth);
        }
    }

    private void initWarp(String name, int x, int y, Map<Point, DijkstraPoint> points, Map<String, Warp> warps) {
        Warp w = new Warp(name, x, y);
        w.outer = x < 3 || x > maxX -3 || y < 3 || y > maxY -3;
        DijkstraPoint dp = new DijkstraPoint(w.toString());
        dp.setObj(w);
        points.put(w, dp);
        if(warps.containsKey(name)) {
            Warp other = warps.get(name);
            if(w.outer == other.outer) {
                throw new IllegalStateException("Warps cannot be both outer/inner");
            } else if(w.getConnectedTo() != null) {
                throw new IllegalStateException("Something wrong!");
            }
            w.id = other.id;
            w.setConnectedTo(other);
            other.setConnectedTo(w);
        } else {
            warps.put(name, w);
            w.id = (char)('A' + warps.size() -1);
        }
    }

    public DonutMaze2(String representation, boolean multidimentional) {
        this.multidimentional = multidimentional;
        this.warps = new HashMap<>();
        Map<String, Warp> warps = new HashMap<>();
        points = new HashMap<>();
        // compute paths
        String[] lines = representation.split("\n");
        maxY = lines.length;
        maxX = lines[0].length();
        try {
            // retrieve all points and warps
            for(int y=0; y<lines.length; y++) {
                for(int x=0; x<lines[y].length(); x++) {
                    if(lines[y].charAt(x) == TYPE_OPEN) {
                        Point p = new Point(x,y);
                        DijkstraPoint dp = new DijkstraPoint(p.toString());
                        dp.setObj(p);
                        points.put(p, dp);
                        continue;
                    }
                    // horizontal warp
                    if(Character.isLetter(lines[y].charAt(x)) && x > 0 && Character.isLetter(lines[y].charAt(x-1))) {
                        String warpName = "" + lines[y].charAt(x-1) + lines[y].charAt(x);
                        if(x+1 < maxX && lines[y].charAt(x+1) == '.') { initWarp(warpName, x, y, points, warps); }
                        else { initWarp(warpName, x-1, y, points, warps); }
                    }
                    // vertical warp
                    else if(Character.isLetter(lines[y].charAt(x)) && y > 0 && Character.isLetter(lines[y-1].charAt(x))) {
                        String warpName = "" +  lines[y-1].charAt(x) + lines[y].charAt(x);
                        if(y+1 < maxY && lines[y+1].charAt(x) == '.') { initWarp(warpName, x, y, points, warps); }
                        else { initWarp(warpName, x, y-1, points, warps); }
                    }
                }
            }
            // build all connexions
            for(int y=1; y<maxY; y++) {
                for (int x=1; x < maxX; x++) {
                    DijkstraPoint p = points.get(new Point(x,y));
                    if(p != null) {
                        DijkstraPoint pN = points.get(new Point(x, y-1)); // north
                        DijkstraPoint pW = points.get(new Point(x-1, y)); // west
                        if(pN != null) {
                            p.addConnection(pN, p.getObj() instanceof DonutMaze2.Warp || pN.getObj() instanceof DonutMaze2.Warp ? 0 : 1);
                        }
                        if(pW != null) {
                            p.addConnection(pW, p.getObj() instanceof DonutMaze2.Warp || pW.getObj() instanceof DonutMaze2.Warp ? 0 : 1);
                        }
                    }
                }
            }
            // build all same-dimensional connexions
            List<Warp> warpList = new ArrayList<>(warps.values());
            for(Warp w : warps.values()) {
                if(w.getConnectedTo() != null) { warpList.add(w.getConnectedTo()); }
            }
            for(int f=0; f<warpList.size(); f++) {
                Warp wFrom = warpList.get(f);
                if(Log.DEBUG) { Log.d(String.format("Distances from %s ... (%d points)", wFrom, points.size())); }
                Dijkstra dijkstra = new Dijkstra(new ArrayList<>(points.values()));
                List<Node> nodes = dijkstra.getShortestPaths(points.get(wFrom));
                for(Node n : nodes) {
                    DijkstraPoint target = (DijkstraPoint) n.getObject();
                    if(n.getDistance() < Integer.MAX_VALUE && target.getObj() instanceof Warp && !target.getObj().equals(wFrom)) {
                        Warp wTo = (Warp)target.getObj();
                        DijkstraPoint warpFrom = this.warps.getOrDefault(wFrom, new DijkstraPoint(wFrom.toString(), wFrom));
                        DijkstraPoint warpTo = this.warps.getOrDefault(wTo, new DijkstraPoint(wTo.toString(), wTo));
                        warpFrom.addConnection(warpTo, n.getDistance());
                        this.warps.put(wFrom, warpFrom);
                        this.warps.put(wTo, warpTo);
                        if(warpFrom.getName().startsWith("AA0-O")) { start = warpFrom; }
                        if(warpFrom.getName().startsWith("ZZ0-O")) { end = warpFrom; }
                    }
                }
            }
            // build warp connexions (interconnect warps)
            if(!multidimentional) {
                for(Warp w : this.warps.keySet()) {
                    if(w.getConnectedTo() != null) {
                        this.warps.get(w).addConnection(this.warps.get(w.getConnectedTo()), 1);
                    }
                }
            } else {
                // assuming max depth < number of warps
                warpsMulti = new HashMap<>();
                for(int d=0; d<warps.size(); d++) {
                    // for each depth copy depth 0
                    for(DijkstraPoint dp : this.warps.values()) {
                        Warp warp = new Warp((Warp) dp.getObj(), d);
                        DijkstraPoint copy = new DijkstraPoint(warp.toString(), warp);
                        warpsMulti.put(copy.getObj().toString(), copy);
                    }
                    for(DijkstraPoint dp : this.warps.values()) {
                        // copy connexions for each warps
                        for(DijkstraPoint dpTo : dp.getConnections()) {
                            Warp warp = (Warp)dpTo.getObj();
                            String dpFromNew = ((Warp)dp.getObj()).toString(d);
                            String dpToNew = warp.toString(d);
                            warpsMulti.get(dpFromNew).addConnection(warpsMulti.get(dpToNew), dp.getDistanceTo(dpTo));
                        }
                        // connect outer to inner (prev depth)
                        if(d>0) {
                            Warp warp = (Warp)dp.getObj();
                            Warp outer = warp.outer ? warp : warp.connectedTo;
                            Warp inner = warp.outer ? warp.connectedTo : warp;
                            if(inner != null) {
                                String outerCur = outer.toString(d);
                                String innerPrev = inner.toString(d - 1);
                                warpsMulti.get(outerCur).addConnection(warpsMulti.get(innerPrev), 1);
                            }
                        }
                    }
                }
            }

        } catch(StringIndexOutOfBoundsException e) {
            throw new IllegalStateException("Input maze is not valid. Lines don't have same length! " +
                    "Make sure your editor didn't automatically remove empty spaces at the end of each line.");
        }
    }

    public Node getShortestPath() {
        if(multidimentional) {
            Dijkstra dijkstra = new Dijkstra(new ArrayList<>(warpsMulti.values()));
            return dijkstra.getShortestPath(warpsMulti.get(start.toString()), warpsMulti.get(end.toString()));
        } else {
            Dijkstra dijkstra = new Dijkstra(new ArrayList<>(warps.values()));
            return dijkstra.getShortestPath(start, end);
        }
    }

    public String dumpMaze() {
        StringBuffer buf = new StringBuffer(maxX*maxY);
        for(int y=0; y<maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                Point curPos = new Point(x,y);
                if(points.containsKey(curPos)) {
                    curPos = (Point)points.get(curPos).getObj();
                    if(curPos instanceof  Warp) {
                        Warp w = (Warp)curPos;
                        buf.append(w.name.equals("AA") ? '@' : w.name.equals("ZZ") ? '?' : w.id);
                    } else {
                        buf.append('.');
                    }
                } else {
                    buf.append('#');
                }
            }
            buf.append('\n');
        }
        buf.append("Distances:\n");
        // dump distances
        List<DijkstraPoint> list = new ArrayList<>(warps.values());
        list.sort(new Comparator<DijkstraPoint>() {
            @Override
            public int compare(DijkstraPoint dp1, DijkstraPoint dp2) {
                return ((Warp) dp1.getObj()).name.compareTo(((Warp) dp2.getObj()).name);
            }
        });
        for(DijkstraPoint dpFrom : list) {
            buf.append(String.format("- %s (%s): ", dpFrom.getName(), ((Warp)dpFrom.getObj()).id));
            for(DijkstraPoint dpTo : dpFrom.getConnections()) {
                buf.append(String.format("(%s:%d), ", dpTo.getName(), dpFrom.getDistanceTo(dpTo)));
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}

