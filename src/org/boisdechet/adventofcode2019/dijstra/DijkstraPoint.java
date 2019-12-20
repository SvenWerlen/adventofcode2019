package org.boisdechet.adventofcode2019.dijstra;

import org.boisdechet.adventofcode2019.coord.Point;

import java.util.HashMap;
import java.util.Map;

public class DijkstraPoint implements INodeObject {

    private String name; // unique Id
    private Object obj;  // object to store (if any)
    private Map<DijkstraPoint, Integer> connections;

    public DijkstraPoint(String name) {
        this.name = name;
        this.connections = new HashMap<>();
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public synchronized void addConnection(DijkstraPoint target, int length) {
        if(!connections.containsKey(target)) {
            connections.put(target, length);
            target.addConnection(this, length);
        }
    }

    @Override
    public String getUniqueId() {
        return name;
    }

    @Override
    public boolean pathExists(INodeObject obj) {
        return connections.containsKey(obj);
    }

    @Override
    public int getDistanceTo(INodeObject obj) {
        if(!connections.containsKey(obj)) {
            throw new IllegalArgumentException(String.format("No path exists between %s and %s", this.name, obj.toString()));
        }
        return connections.get((DijkstraPoint) obj);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point && ((DijkstraPoint)obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
