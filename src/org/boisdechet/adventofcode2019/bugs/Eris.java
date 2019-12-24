package org.boisdechet.adventofcode2019.bugs;

import java.util.HashSet;
import java.util.Set;

public class Eris {

    private boolean[] tiles;
    private int size;

    public Eris(String representation) {
        String[] lines = representation.split("\n");
        tiles = new boolean[lines.length * lines[0].length()];
        int idx = 0;
        for(String line : lines) {
            for(char c : line.toCharArray()) {
                tiles[idx++] = c == '#';
            }
        }
        size = lines[0].length();
    }

    private int bugCount(int idx) {
        int count = 0;
        count += idx-size >= 0 && tiles[idx-size] ? 1 : 0;           // top
        count += idx % size != size-1 && tiles[idx+1] ? 1 : 0;       // right
        count += idx+size < tiles.length && tiles[idx+size] ? 1 : 0; // bottom
        count += idx % size != 0 && tiles[idx-1] ? 1 : 0;            // left
        return count;
    }

    public void oneMinuteLater() {
        boolean[] newTiles = new boolean[tiles.length];
        for(int i=0; i<tiles.length; i++) {
            // A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
            int count = bugCount(i);
            if(tiles[i]) {
                newTiles[i] = count == 1;
            }
            // An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
            else {
                newTiles[i] = count == 1 || count == 2;
            }
        }
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
        for(int i=0; i<tiles.length; i++) {
            if(tiles[i]) {
                total += Math.pow(2, i);
            }
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        int idx = 1;
        for(boolean b : tiles) {
            buf.append(b ? '#' : '.');
            if(idx++ % size == 0) {
                buf.append('\n');
            }
        }
        return buf.toString();
    }
}
