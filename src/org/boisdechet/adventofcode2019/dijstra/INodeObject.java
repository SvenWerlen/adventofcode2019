package org.boisdechet.adventofcode2019.dijstra;

public interface INodeObject {
    String getUniqueId();
    boolean pathExists(INodeObject target);
    int getDistanceTo(INodeObject obj);
}
