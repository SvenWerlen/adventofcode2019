package org.boisdechet.adventofcode2019.dijkstra;

import org.boisdechet.adventofcode2019.dijstra.Dijkstra;
import org.boisdechet.adventofcode2019.dijstra.INodeObject;
import org.boisdechet.adventofcode2019.dijstra.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test based on example: https://www.baeldung.com/java-dijkstra
 */
public class DijkstraTest {

    public static class Point implements INodeObject {

        private String name;
        private Map<Point, Integer> connections;

        public Point(String name) {
            this.name = name;
            this.connections = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public synchronized void addConnection(Point point, int length) {
            if(!connections.containsKey(point)) {
                connections.put(point, length);
                point.addConnection(this, length);
            }
        }

        @Override
        public boolean pathExists(Node curNode, Node obj) {
            return connections.containsKey((Point)obj.getObject());
        }

        @Override
        public int getDistanceTo(INodeObject obj) {
            if(!connections.containsKey(obj)) {
                throw new IllegalArgumentException(String.format("No path exists between %s and %s", this.name, obj.toString()));
            }
            return connections.get((Point)obj);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Point && ((Point)obj).name.equals(this.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public String toString() {
            return name;
        }
    };

    @Test
    public void example1() throws Exception {
        List<INodeObject> list = new ArrayList<>();
        Point pointA = new Point("A"); list.add(pointA);
        Point pointB = new Point("B"); list.add(pointB);
        Point pointC = new Point("C"); list.add(pointC);
        Point pointD = new Point("D"); list.add(pointD);
        Point pointE = new Point("E"); list.add(pointE);
        Point pointF = new Point("F"); list.add(pointF);
        pointA.addConnection(pointB, 10);
        pointA.addConnection(pointC, 15);
        pointB.addConnection(pointD, 12);
        pointC.addConnection(pointE, 10);
        pointB.addConnection(pointF, 15);
        pointD.addConnection(pointE, 2);
        pointD.addConnection(pointF, 1);
        pointF.addConnection(pointE, 5);
        // shortest path from A to E
        Node result = new Dijkstra(list).getShortestPath(pointA, pointE);
        assertEquals(24, result.getDistance());
        StringBuffer buf = new StringBuffer(result.toString());
        Node cur = result;
        while((cur = cur.getPreviousNode()) != null) {
            buf.append(cur.toString());
        }
        assertEquals( "ABDE", buf.reverse().toString());
    }
}
