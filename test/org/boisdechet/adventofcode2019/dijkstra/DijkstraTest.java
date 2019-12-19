package org.boisdechet.adventofcode2019.dijkstra;

import org.boisdechet.adventofcode2019.dijstra.Dijkstra;
import org.boisdechet.adventofcode2019.dijstra.DijkstraDynamic;
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

    static class Point implements INodeObject {

        private String name;
        private Map<Point, Integer> connections;

        private Point(String name) {
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
        public String getUniqueId() {
            return name;
        }

        @Override
        public boolean pathExists(INodeObject obj) {
            return connections.containsKey(obj);
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

    static class DijkstraController implements DijkstraDynamic.Controller {

        private List<INodeObject> list;
        private String targetId;

        private DijkstraController(List<INodeObject> list, String targetId) {
            this.list = list;
            this.targetId = targetId;
        }

        @Override
        public List<INodeObject> getConnectedNodes(Node from) {
            List<INodeObject> result = new ArrayList<>();
            for(INodeObject o : list) {
                if(from.getObject().pathExists(o)) {
                    result.add(o);
                }
            }
            return result;
        }

        @Override
        public int getDistance(Node from, INodeObject to) {
            return from.getObject().getDistanceTo(to);
        }

        @Override
        public boolean targetReached(Node target) {
            return target.getUniqueId().equals(targetId);
        }
    };

    @Test
    public void simple() throws Exception {
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
        // shortest path from A to E (Dijkstra simple)
        Node result = new Dijkstra(list).getShortestPath(pointA, pointE);
        assertEquals(24, result.getDistance());
        StringBuffer buf = new StringBuffer(result.toString());
        Node cur = result;
        while((cur = cur.getPreviousNode()) != null) {
            buf.append(cur.toString());
        }
        assertEquals( "ABDE", buf.reverse().toString());
    }

    @Test
    public void dynamic() throws Exception {
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
        // shortest path from A to E (Dijkstra Dynamic)
        DijkstraDynamic.Controller controller = new DijkstraController(list, "E");
        Node result = new DijkstraDynamic(controller).getShortestPath(pointA);
        assertEquals(24, result.getDistance());
        StringBuffer buf = new StringBuffer(result.toString());
        Node cur = result;
        while((cur = cur.getPreviousNode()) != null) {
            buf.append(cur.toString());
        }
        assertEquals( "ABDE", buf.reverse().toString());
    }
}
