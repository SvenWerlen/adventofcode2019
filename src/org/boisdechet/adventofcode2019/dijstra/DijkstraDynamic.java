package org.boisdechet.adventofcode2019.dijstra;

import org.boisdechet.adventofcode2019.coord.VaultMultiKey;
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


    public DijkstraDynamic(Controller controller) {
        this.controller = controller;
    }

    public Node getShortestPath(INodeObject srcObj) {
        // prepare graph
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> history = new HashSet<>();
        queue.add(new Node(srcObj, 0));
        // algorithm
        Node curNode = null;
        long iter = 1;
        while(queue.size() > 0) {
            curNode = queue.poll();
            String uniqueId = controller.getPathUniqueId(curNode.getPreviousNode(), curNode.getObject());
            if(Log.DEBUG) { Log.d(String.format("Current node is %s (%s/%s) with distance %d (%d)", curNode, curNode.getFullPath(), uniqueId, curNode.getDistance(), System.identityHashCode(curNode))); }
            if(controller.targetReached(curNode)) {
                return curNode;
            }
            else if(history.contains(uniqueId)) {
                if(Log.DEBUG) { Log.d(String.format("\tPath %s already in history!", uniqueId)); }
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
                    if(Log.DEBUG) { Log.d(String.format("\tPath %s already in history", pathUniqueID)); }
                    continue;
                }
                int dist = controller.getDistance(curNode, o);
                if(Log.DEBUG) { Log.d(String.format("\tPath exists between %s and %s (dist = %d)", curNode, o, dist)); }
                Node target = new Node(o);
                target.setPreviousNode(curNode);
                target.setDistance(curNode.getDistance() + dist);
                if(queue.contains(target)) {
                    throw new IllegalStateException(String.format("Something went wrong! Adding element %s while already in queue!", target.toString()));
                }
                queue.add(target);
                if(Log.DEBUG) { Log.d(String.format("\t\tAdded in queue: %s (dist: %d) - %d", ((VaultMultiKey)target.getObject()).toFullString(), target.getDistance(), System.identityHashCode(target))); }
            }
            iter++;
        }
        Log.w("Target not reached... returning best node!");
        return curNode;
    }
}
