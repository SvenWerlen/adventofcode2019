package org.boisdechet.adventofcode2019.dijstra;

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
        StringBuffer buf = new StringBuffer(toString());
        Node cur = this;
        while((cur = cur.getPreviousNode()) != null) {
            buf.append(cur.toString());
        }
        return buf.reverse().toString();
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
