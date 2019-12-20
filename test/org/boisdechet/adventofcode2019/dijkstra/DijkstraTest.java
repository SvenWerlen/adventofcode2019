package org.boisdechet.adventofcode2019.dijkstra;

import org.boisdechet.adventofcode2019.dijstra.*;
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

        @Override
        public String getPathUniqueId(Node from, INodeObject to) {
            return to.getUniqueId();
        }
    };

    @Test
    public void simple() throws Exception {
        List<INodeObject> list = new ArrayList<>();
        DijkstraPoint pointA = new DijkstraPoint("A"); list.add(pointA);
        DijkstraPoint pointB = new DijkstraPoint("B"); list.add(pointB);
        DijkstraPoint pointC = new DijkstraPoint("C"); list.add(pointC);
        DijkstraPoint pointD = new DijkstraPoint("D"); list.add(pointD);
        DijkstraPoint pointE = new DijkstraPoint("E"); list.add(pointE);
        DijkstraPoint pointF = new DijkstraPoint("F"); list.add(pointF);
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
        DijkstraPoint pointA = new DijkstraPoint("A"); list.add(pointA);
        DijkstraPoint pointB = new DijkstraPoint("B"); list.add(pointB);
        DijkstraPoint pointC = new DijkstraPoint("C"); list.add(pointC);
        DijkstraPoint pointD = new DijkstraPoint("D"); list.add(pointD);
        DijkstraPoint pointE = new DijkstraPoint("E"); list.add(pointE);
        DijkstraPoint pointF = new DijkstraPoint("F"); list.add(pointF);
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
