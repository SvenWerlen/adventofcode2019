package org.boisdechet.adventofcode2019.dijstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node implements Comparable<Node> {

    private INodeObject data;
    private Node prev;
    private int distance;

    public Node(INodeObject data) {
        this.data = data;
        this.prev = null;
        this.distance = Integer.MAX_VALUE;
    }

    public Node(INodeObject data, int distance) {
        this(data);
        this.distance = distance;
    }

    public INodeObject getObject() {
        return this.data;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        if(distance >= this.distance) {
            throw new IllegalArgumentException("Node distance can only decrease!");
        }
        this.distance = distance;
    }

    public Node getPreviousNode() {
        return this.prev;
    }

    public void setPreviousNode(Node node) {
        if(node == null) {
            throw new IllegalArgumentException("Previous node cannot be set to null!");
        }
        this.prev = node;
    }

    public String getUniqueId() {
        return this.data.getUniqueId();
    }

    public String getFullPath() {
        List<String> path = new ArrayList<>();
        path.add(toString());
        Node cur = this;
        while((cur = cur.getPreviousNode()) != null) {
            path.add(cur.toString());
        }
        Collections.reverse(path);
        StringBuffer buf = new StringBuffer();
        for(String s : path) {
            buf.append(s).append('|');
        }
        return buf.toString();
    }

    @Override
    public int hashCode() {
        return this.data.getUniqueId().hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(distance, node.distance);
    }
}
