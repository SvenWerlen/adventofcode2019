package org.boisdechet.adventofcode2019.dijstra;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {

    private List<INodeObject> objects;

    public Dijkstra(List<INodeObject> objects) {
        if(objects == null || objects.size() <= 2) {
            throw new IllegalArgumentException("List of objects must contain at least 2 elements!");
        }
        this.objects = objects;
    }

    public Node getShortestPath(INodeObject srcObj, INodeObject dstObj) {
        // prepare graph
        List<Node> nodes = new ArrayList<>();
        Node src = null;
        Node dst = null;
        for(INodeObject obj : objects) {
            Node node = new Node(obj);
            nodes.add(node);
            if(obj == srcObj) { src = node; }
            if(obj == dstObj) { dst = node; }
        }
        // check that source and destination are part of the list
        if(src == null) {
            throw new IllegalStateException("Source or Destination are not included in list!");
        }
        // algorithm
        src.setDistance(0);
        Node curNode = src;
        while(nodes.size() > 1) {
            if(curNode.equals(dst)) {
                return curNode;
            }
            nodes.remove(curNode);
            Log.d("");
            Log.d(String.format("Current node is %s with distance %d", curNode, curNode.getDistance()));
            // compute distances for nodes "around"
            for(Node n : nodes) {
                if(curNode.getObject().pathExists(curNode, n)) {
                    int dist = curNode.getObject().getDistanceTo(n.getObject());
                    Log.d(String.format("Path exists between %s and %s (dist = %d)", curNode, n, dist));
                    if(curNode.getDistance() + dist < n.getDistance()) {
                        n.setPreviousNode(curNode);
                        n.setDistance(curNode.getDistance() + dist);
                        Log.d(String.format("Setting distance in %s to %d", n, n.getDistance()));
                    }
                } else {
                    Log.d(String.format("Path doesn't exists between %s and %s", curNode, n));
                }
            }
            // search next best node
            Node minNode = null;
            for(Node n : nodes) {
                if(minNode == null || n.getDistance() < minNode.getDistance()) {
                    minNode = n;
                }
            }
            curNode = minNode;
        }
        return curNode;
    }
}
