package org.boisdechet.adventofcode2019.dijstra;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.*;

/**
 * Dijkstra: dynamic implementation
 * List of objects is not known in advance
 */
public class DijkstraDynamic {

    public interface Controller {
        List<INodeObject> getConnectedNodes(Node from);
        int getDistance(Node from, INodeObject to);
        boolean targetReached(Node target);
    }

    private Controller controller;
    private PriorityQueue<Node> queue;

    public DijkstraDynamic(Controller controller) {
        this.controller = controller;
        this.queue = new PriorityQueue<>();
    }

    public Node getShortestPath(INodeObject srcObj) {
        // prepare graph
        queue.add(new Node(srcObj, 0));
        // algorithm
        Node curNode = null;
        while(queue.size() > 0) {
            curNode = queue.poll();
            if(controller.targetReached(curNode)) {
                return curNode;
            }
            Log.i("" + curNode.getDistance());
            Log.d(String.format("Current node is %s with distance %d", curNode, curNode.getDistance()));
            // retrieve all nodes "connected"
            List<INodeObject> list = controller.getConnectedNodes(curNode);
            // compute distances for nodes "around"
            for(INodeObject o : list) {
                int dist = controller.getDistance(curNode, o);
                Log.d(String.format("Path exists between %s and %s (dist = %d)", curNode, o, dist));
                Node target = new Node(o);
                target.setPreviousNode(curNode);
                target.setDistance(curNode.getDistance() + dist);
                queue.add(target);
            }
        }
        return curNode;
    }
}
