package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.AsteroidsMap;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class Day10Test {

    @Test
    public void utils() throws Exception {
        List<Point> list = InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,0),'#');
        assertEquals(10, list.size());
        AsteroidsMap map = new AsteroidsMap(list);
        assertFalse(map.hasLineOfSight(new Point(0,2), new Point(4,2)));
        assertTrue(map.hasLineOfSight(new Point(0,2), new Point(4,0)));
        assertFalse(map.hasLineOfSight(new Point(4,0), new Point(4,3)));
        assertEquals(7, map.getVisibleAsteroids(new Point(1,0)));
        assertEquals(8, map.getVisibleAsteroids(new Point(3,4)));
    }

    @Test
    public void examples() throws Exception {
        // part I
        AsteroidsMap.BestLocation location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,0),'#')).getBestLocation();
        assertEquals(new Point(3,4), location.point);
        assertEquals(8, location.visibleAsteroids);
        location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,1),'#')).getBestLocation();
        assertEquals(new Point(5,8), location.point);
        assertEquals(33, location.visibleAsteroids);
        location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,2),'#')).getBestLocation();
        assertEquals(new Point(1,2), location.point);
        assertEquals(35, location.visibleAsteroids);
        location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,3),'#')).getBestLocation();
        assertEquals(new Point(6,3), location.point);
        assertEquals(41, location.visibleAsteroids);
        location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,4),'#')).getBestLocation();
        assertEquals(new Point(11,13), location.point);
        assertEquals(210, location.visibleAsteroids);
    }

}