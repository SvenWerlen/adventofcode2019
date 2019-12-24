package org.boisdechet.adventofcode2019.bugs;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.HashSet;
import java.util.Set;

public class Eris {

    private final int SIZE = 5;

    protected Eris layerBelow, layerAbove;
    protected boolean[][] tiles;
    private boolean multilayers;
    private int depth;

    public Eris(Eris layerBelow, Eris layerAbove, int depth) {
        this.layerBelow = layerBelow;
        this.layerAbove = layerAbove;
        this.multilayers = true;
        this.depth = depth;
        tiles = new boolean[][] {new boolean[SIZE], new boolean[SIZE], new boolean[SIZE], new boolean[SIZE], new boolean[SIZE]};
    }

    public Eris(String representation) {
        this(representation, false);
    }

    public Eris(String representation, boolean multilayers) {
        this(null, null, 0);
        representation = representation.replaceAll("\n", "");
        if(representation.length() != SIZE*SIZE) {
            throw new IllegalStateException(String.format("Illegal Eris representation. Must have 25 squares not %d", representation.length()));
        }
        for(int y=0; y<SIZE; y++) {
            for(int x=0; x<SIZE; x++) {
                tiles[y][x] = representation.charAt(y*SIZE+x) == '#';
            }
        }
        this.multilayers = multilayers;
    }

    private int bugCount(int x, int y) {
        int count = 0;
        // special case
        if(multilayers && x == 2 && y == 2) {
            return 0;
        }
        count += y > 0 && tiles[y - 1][x] ? 1 : 0;      // top
        count += x + 1 < SIZE && tiles[y][x + 1] ? 1 : 0; // right
        count += y + 1 < SIZE && tiles[y + 1][x] ? 1 : 0; // bottom
        count += x > 0 && tiles[y][x - 1] ? 1 : 0;      // left
        // increment based on layer above or below
        if(multilayers) {
            if(layerAbove != null) {
                if (y == 0) { count += layerAbove.tiles[1][2] ? 1 : 0; }
                if (y == SIZE - 1) { count += layerAbove.tiles[3][2] ? 1 : 0; }
                if (x == 0) { count += layerAbove.tiles[2][1] ? 1 : 0; }
                if (x == SIZE - 1) { count += layerAbove.tiles[2][3] ? 1 : 0; }
            }
            if(layerBelow != null) {
                if(y == 2 && x == 1) { count += layerBelow.bugsOnColumn(0); }
                if(y == 2 && x == 3) { count += layerBelow.bugsOnColumn(SIZE-1); }
                if(y == 1 && x == 2) { count += layerBelow.bugsOnLine(0); }
                if(y == 3 && x == 2) { count += layerBelow.bugsOnLine(SIZE-1); }
            }
        }
        return count;
    }

    protected int bugsOnLine(int y) {
        int count = 0;
        for(int x=0; x<SIZE; x++) {
            if(tiles[y][x]) { count++; }
        }
        return count;
    }

    protected int bugsOnColumn(int x) {
        int count = 0;
        for(int y=0; y<SIZE; y++) {
            if(tiles[y][x]) { count++; }
        }
        return count;
    }

    public int bugsCount() {
        return bugsCount(false, false);
    }

    public int bugsCount(boolean includeBelow, boolean includeAbove) {
        int count = 0;
        for(int y=0; y<SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if (tiles[y][x]) {
                    count++;
                }
            }
        }
        if(includeBelow && layerBelow != null) {
            count += layerBelow.bugsCount(true, false);
        }
        if(includeAbove && layerAbove != null) {
            count += layerAbove.bugsCount(false, true);
        }
        return count;
    }

    public void oneMinuteLater() {
        oneMinuteLater(false, false);
    }

    public void oneMinuteLater(boolean above, boolean below) {
        boolean[][] newTiles = new boolean[][] {new boolean[SIZE], new boolean[SIZE], new boolean[SIZE], new boolean[SIZE], new boolean[SIZE]};
        for(int y=0; y<SIZE; y++) {
            for(int x=0; x<SIZE; x++) {
                // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
                int count = bugCount(x,y);
                if (tiles[y][x]) {
                    newTiles[y][x] = count == 1;
                }
                // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
                else {
                    newTiles[y][x] = count == 1 || count == 2;
                }
            }
        }
        // check if above/below layers must be created
        if(multilayers) {
            int bugs = bugsCount();
            if (bugs > 0 && layerAbove == null) {
                layerAbove = new Eris(this, null, depth-1);
            }
            if(bugs > 0 && layerBelow == null) {
                layerBelow = new Eris(null, this, depth+1);
            }
            if(above && layerAbove != null) {
                layerAbove.oneMinuteLater(true, false);
            }
            if(below && layerBelow != null) {
                layerBelow.oneMinuteLater(false, true);
            }
        }
        // update
        tiles = newTiles;
    }

    public int untilFirstMatch() {
        Set<String> layers = new HashSet<>();
        String layer = toString();
        while(!layers.contains(layer)) {
            layers.add(toString());
            oneMinuteLater();
            layer = toString();
        }
        return layers.size();
    }

    public long getBiodiversityRating() {
        int total = 0;
        for(int y=0; y<SIZE; y++) {
            for(int x=0; x<SIZE; x++) {
                if(tiles[y][x]) {
                    total += Math.pow(2, y*SIZE+x);
                }
            }
        }
        return total;
    }

    public String dump(boolean below, boolean above) {
        if(bugsCount() == 0) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        if(above && layerAbove != null) {
            buf.append(layerAbove.dump(false, true));
        }
        buf.append("Depth ").append(depth).append(':').append('\n').append(toString()).append('\n');
        if(below && layerBelow != null) {
            buf.append(layerBelow.dump(true, false));
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        int idx = 1;
        for(int y=0; y<SIZE; y++) {
            for(int x=0; x<SIZE; x++) {
                buf.append(multilayers && y == 2 && x == 2 ? '?' : tiles[y][x] ? '#' : '.');
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
