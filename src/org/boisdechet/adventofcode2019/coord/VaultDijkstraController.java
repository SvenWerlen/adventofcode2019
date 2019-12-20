package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.DijkstraDynamic;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;

import java.util.*;

/**
 * Class for dynamic Dijkstra.
 * Each node represents a key and previous sequences (previous keys)
 */
public class VaultDijkstraController implements DijkstraDynamic.Controller {

    private List<VaultKey> keys;

    public VaultDijkstraController(List<VaultKey> keys) {
        this.keys = keys;
    }

    /**
     * Connected nodes are only the keys that are reachable considering the available keys (path)
     */
    @Override
    public List<INodeObject> getConnectedNodes(Node from) {
        // build available keys
        Map<Character, VaultKey> hasKeys = new HashMap<>();
        Node cur = from;
        while(cur != null) {
            VaultKey k = (VaultKey)cur.getObject();
            hasKeys.put(k.getId(), k);
            cur = cur.getPreviousNode();
        }

        // build connected nodes
        List<INodeObject> result = new ArrayList<>();
        VaultKey curKey = (VaultKey)from.getObject();
        for(VaultKey k : keys) {
            if(!hasKeys.containsKey(k.getId())) {
                // check dependencies
                boolean depOK = true;
                for(Character dep : curKey.requires.getOrDefault(k.id, new HashSet<>())) {
                    if(!hasKeys.containsKey(dep)) {
                        depOK = false; break;
                    }
                }
                if(depOK) {
                    result.add(k);
                }
            }
        }
        return result;
    }

    @Override
    public int getDistance(Node from, INodeObject to) {
        return from.getObject().getDistanceTo(to);
    }

    /**
     * Returns true if all keys have been collected
     */
    @Override
    public boolean targetReached(Node target) {
        int count = 1;
        Node curNode = target;
        while((curNode = curNode.getPreviousNode()) != null) {
            count++;
        }
        return count == keys.size();
    }

    /**
     * Unique Id is based on current key and history
     * Two paths containing the same keys and ending with the same key must be considered the same
     * (Without that optimization, algorithm takes hours to execute)
     */
    @Override
    public String getPathUniqueId(Node from, INodeObject to) {
        if(from == null) {
            return to.getUniqueId();
        }
        PriorityQueue<Character> visited = new PriorityQueue<>();
        Node curNode = from;
        while(curNode != null) {
            visited.add(((VaultKey)curNode.getObject()).getId());
            curNode = curNode.getPreviousNode();
        }
        StringBuffer buf = new StringBuffer(visited.size());
        while(!visited.isEmpty()) {
            buf.append(visited.poll());
        }
        if(to != null) { buf.append(((VaultKey)to).getId()); }
        return buf.toString();
    }
}