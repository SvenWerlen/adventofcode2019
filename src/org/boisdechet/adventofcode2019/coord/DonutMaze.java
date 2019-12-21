package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.Dijkstra;
import org.boisdechet.adventofcode2019.dijstra.DijkstraPoint;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonutMaze {

    private static final char TYPE_OPEN = '.';

    private int maxX, maxY;
    private Map<Point, DijkstraPoint> points;
    private Map<String, Warp> warps;

    public static class Warp extends Point {
        private char id;
        private String name;
        private Warp connectedTo;

        public Warp(String name, int x, int y) {
            super(x, y);
            this.name = name;
        }

        public Warp getConnectedTo() {
            return connectedTo;
        }

        public void setConnectedTo(Warp other) {
            this.connectedTo = other;
        }

        @Override
        public String toString() {
            return this.name + super.toString();
        }
    }

    public static void initWarp(String name, int x, int y, Map<Point, DijkstraPoint> points, Map<String, Warp> warps) {
        Warp w = new Warp(name, x, y);
        DijkstraPoint dp = new DijkstraPoint(w.name);
        dp.setObj(w);
        points.put(w, dp);
        if(warps.containsKey(name)) {
            Warp other = warps.get(name);
            w.id = other.id;
            w.setConnectedTo(other);
            other.setConnectedTo(w);
            dp.addConnection(points.get(other), 1);
        } else {
            warps.put(name, w);
            w.id = (char)('A' + warps.size() -1);
        }
    }

    public DonutMaze(String representation) {
        points = new HashMap<>();
        warps = new HashMap<>();
        // compute paths
        String[] lines = representation.split("\n");
        maxY = lines.length;
        maxX = lines[0].length();
        try {
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
                            p.addConnection(pN, p.getObj() instanceof Warp || pN.getObj() instanceof Warp ? 0 : 1);
                        }
                        if(pW != null) {
                            p.addConnection(pW, p.getObj() instanceof Warp || pW.getObj() instanceof Warp ? 0 : 1);
                        }
                    }
                }
            }
        } catch(StringIndexOutOfBoundsException e) {
            throw new IllegalStateException("Input maze is not valid. Lines don't have same length! Make sure your editor didn't automatically remove empty spaces at the end of each line.");
        }
    }

    public Node getShortestPath() {
        Dijkstra dijkstra = new Dijkstra(new ArrayList<>(points.values()));
        return dijkstra.getShortestPath(points.get(warps.get("AA")), points.get(warps.get("ZZ")));
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
        return buf.toString();
    }
}

