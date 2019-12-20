package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.dijstra.DijkstraDynamic;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;

import java.util.*;

/**
 * Class for dynamic Dijkstra.
 * Each node represents a key and previous sequences (previous keys)
 */
public class VaultDijkstraMultiController implements DijkstraDynamic.Controller {

    private boolean speedup;
    private List<VaultKey> keys;

    public VaultDijkstraMultiController(List<VaultKey> keys, boolean speedup) {
        this.keys = keys;
        this.speedup = speedup;
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
            VaultMultiKey k = (VaultMultiKey)cur.getObject();
            hasKeys.put(k.key1.getId(), k.key1);
            hasKeys.put(k.key2.getId(), k.key2);
            hasKeys.put(k.key3.getId(), k.key3);
            hasKeys.put(k.key4.getId(), k.key4);
            cur = cur.getPreviousNode();
        }

        // build connected nodes
        List<INodeObject> result = new ArrayList<>();
        VaultMultiKey curKey = (VaultMultiKey)from.getObject();
        for(VaultKey k : keys) {
            if(!hasKeys.containsKey(k.getId())) {
                // check dependencies
                if(dependencyOK(curKey.key1, k, hasKeys)) {
                    result.add(new VaultMultiKey(k, curKey.key2, curKey.key3, curKey.key4, k));
                } else if(dependencyOK(curKey.key2, k, hasKeys)) {
                    result.add(new VaultMultiKey(curKey.key1, k, curKey.key3, curKey.key4, k));
                } else if(dependencyOK(curKey.key3, k, hasKeys)) {
                    result.add(new VaultMultiKey(curKey.key1, curKey.key2, k, curKey.key4, k));
                } else if(dependencyOK(curKey.key4, k, hasKeys)) {
                    result.add(new VaultMultiKey(curKey.key1, curKey.key2, curKey.key3, k, k));
                }
            }
        }

        return result;
    }

    private static boolean dependencyOK(VaultKey from, VaultKey to, Map<Character, VaultKey> hasKeys) {
        boolean depOK = true;
        // not path
        if(!from.distances.containsKey(to.id)) {
            return false;
        }
        // check dependencies
        for(Character dep : from.requires.getOrDefault(to.id, new HashSet<>())) {
            if(!hasKeys.containsKey(dep)) {
                depOK = false; break;
            }
        }
        return depOK;
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
        int count = 4;
        Node curNode = target;
        while((curNode = curNode.getPreviousNode()) != null) {
            count++;
        }
        return count == keys.size();
    }

    /**
     * Unique Id is based on current key and history
     * Two paths containing the same keys and ending with the same 4 keys must be considered the same
     * (Without that optimization, algorithm takes hours to execute)
     * When speedup is enabled, only two paths containing the same keys and ending with the same 1 key are considered the same (much faster but doesn't work all the time)
     */
    @Override
    public String getPathUniqueId(Node from, INodeObject to) {
        if(from == null) {
            return to.getUniqueId();
        }
        PriorityQueue<Character> visited = new PriorityQueue<>();
        Node curNode = from;

        VaultMultiKey fromKey = (VaultMultiKey)from.getObject();
        String lastKeys = "";
        if(!speedup){
            if (fromKey.key1.id >= 'a') {
                lastKeys += fromKey.key1.id;
            }
            if (fromKey.key2.id >= 'a') {
                lastKeys += fromKey.key2.id;
            }
            if (fromKey.key3.id >= 'a') {
                lastKeys += fromKey.key3.id;
            }
            if (fromKey.key4.id >= 'a') {
                lastKeys += fromKey.key4.id;
            }
        }
        while(curNode != null) {
            if(curNode.getPreviousNode() != null) {
                VaultKey key = ((VaultMultiKey) curNode.getObject()).changed;
                if(speedup || lastKeys.indexOf(key.id)<0) {
                    visited.add(((VaultMultiKey) curNode.getObject()).changed.getId());
                }
            }
            curNode = curNode.getPreviousNode();
        }
        StringBuffer buf = new StringBuffer(visited.size());
        while(!visited.isEmpty()) {
            buf.append(visited.poll());
        }
        if(!speedup) { buf.append(lastKeys); }
        if(to != null) { buf.append(((VaultMultiKey)to).changed.getId()); }
        return buf.toString();
    }
}