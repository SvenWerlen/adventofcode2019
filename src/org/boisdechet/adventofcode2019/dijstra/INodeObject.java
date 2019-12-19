package org.boisdechet.adventofcode2019.dijstra;

public interface INodeObject {
    boolean pathExists(Node obj, Node curNode);
    int getDistanceTo(INodeObject obj);
}
