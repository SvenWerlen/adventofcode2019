package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.INodeObject;

import java.util.*;

/**
 * POJO class which represents one key
 * Used for Dijkstra algorithm
 */
public class VaultKey implements INodeObject {
    Character id;                               // key identifier
    Point position;                             // position of the key in maze
    Map<Character, Integer> distances;          // pre-computed distances
    Map<Character, Set<Character>> requires;    // pre-computed requirements

    public char getId() {
        return id;
    }

    public VaultKey(Character id, Point position) {
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
        return distances.getOrDefault(((VaultKey)obj).id, Integer.MAX_VALUE);
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