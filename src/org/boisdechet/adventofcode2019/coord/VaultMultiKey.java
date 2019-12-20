package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.INodeObject;

import java.util.*;

/**
 * POJO class which represents one key
 * Used for Dijkstra algorithm
 */
public class VaultMultiKey implements INodeObject {

    VaultKey key1, key2, key3, key4;            // 4 different keys
    VaultKey changed;

    public VaultMultiKey(VaultKey key1, VaultKey key2, VaultKey key3, VaultKey key4, VaultKey changed) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.changed = changed;
    }

    @Override
    public String getUniqueId() {
        return changed == null ? "@" : "" + changed.id;
    }

    @Override
    public boolean pathExists(INodeObject obj) {
        throw new IllegalStateException("Not implemented! Don't use simple Dijkstra with Keys");
    }

    @Override
    public int getDistanceTo(INodeObject obj) {
        int dist = key1.getDistanceTo(((VaultMultiKey)obj).changed);
        dist = Math.min(dist, key2.getDistanceTo(((VaultMultiKey)obj).changed));
        dist = Math.min(dist, key3.getDistanceTo(((VaultMultiKey)obj).changed));
        dist = Math.min(dist, key4.getDistanceTo(((VaultMultiKey)obj).changed));
        return dist;
    }

    @Override
    public String toString() {
        return getUniqueId();
    }

    public String toFullString() {
        return String.format("%s (%s,%s,%s,%s)", getUniqueId(), key1, key2, key3, key4);
    }
}