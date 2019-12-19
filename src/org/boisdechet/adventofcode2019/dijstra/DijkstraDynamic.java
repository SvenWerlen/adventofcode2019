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
        String getPathUniqueId(Node from, INodeObject to);
    }

    private Controller controller;
    private PriorityQueue<Node> queue;

    public DijkstraDynamic(Controller controller) {
        this.controller = controller;
        this.queue = new PriorityQueue<>();
    }

    public Node getShortestPath(INodeObject srcObj) {
        // prepare graph
        Set<String> history = new HashSet<>();
        queue.add(new Node(srcObj, 0));
        // algorithm
        Node curNode = null;
        long iter = 1;
        while(queue.size() > 0) {
            curNode = queue.poll();
            String uniqueId = controller.getPathUniqueId(curNode.getPreviousNode(), curNode.getObject());
            if(Log.DEBUG) { Log.d(String.format("Current node is %s with distance %d", curNode, curNode.getDistance())); }
            if(controller.targetReached(curNode)) {
                return curNode;
            }
            else if(history.contains(uniqueId)) {
                continue;
            }
            history.add(uniqueId);
            if(history.size() != iter) {
                throw new IllegalStateException("Something wrong! History size should always match iteration!");
            }
            // retrieve all nodes "connected"
            List<INodeObject> list = controller.getConnectedNodes(curNode);
            // compute distances for nodes "around"
            for(INodeObject o : list) {
                String pathUniqueID = controller.getPathUniqueId(curNode, o);
                if(history.contains(pathUniqueID)) {
                    if(Log.DEBUG) { Log.d(String.format("Path %s already in history", pathUniqueID)); }
                    continue;
                }
                int dist = controller.getDistance(curNode, o);
                if(Log.DEBUG) { Log.d(String.format("Path exists between %s and %s (dist = %d)", curNode, o, dist)); }
                Node target = new Node(o);
                target.setPreviousNode(curNode);
                target.setDistance(curNode.getDistance() + dist);
                queue.add(target);
            }
            iter++;
        }
        return curNode;
    }
}
