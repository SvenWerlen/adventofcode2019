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
        assertEquals(0d, AsteroidsMap.getAngle(new Point(5,5), new Point(5,2)));
        assertEquals(Math.PI/2, AsteroidsMap.getAngle(new Point(5,5), new Point(10,5)));
        assertEquals(Math.PI, AsteroidsMap.getAngle(new Point(5,5), new Point(5,10)));
        assertEquals(3*Math.PI/2, AsteroidsMap.getAngle(new Point(5,5), new Point(0,5)));
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,5),'#'));
        AsteroidsMap.Location loc = map.getNextAsteroid(new Point(8,3),0d);
        assertEquals(new Point(8,1), loc.point);
        assertEquals(0d, loc.angle);
        loc = map.getNextAsteroid(new Point(8,3),loc.angle+0.001);
        assertEquals(new Point(9,0), loc.point);
        loc = map.getNextAsteroid(new Point(8,3),loc.angle+0.001);
        assertEquals(new Point(9,1), loc.point);
        loc = map.getNextAsteroid(new Point(8,3),loc.angle+0.001);
        assertEquals(new Point(10,0), loc.point);
        loc = map.getNextAsteroid(new Point(8,3),loc.angle+0.001);
        assertEquals(new Point(9,2), loc.point);
        loc = map.getNextAsteroid(new Point(8,3),loc.angle+0.001);
        assertEquals(new Point(11,1), loc.point);
    }

    @Test
    public void examples() throws Exception {
        // part I
        AsteroidsMap.Location location = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,0),'#')).getBestLocation();
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
        // part II
        AsteroidsMap map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,5),'#'));
        assertEquals(new Point(15,1), map.getVaporizedAsteroid(new Point(8,3), 9).point);
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,5),'#'));
        assertEquals(new Point(4,4), map.getVaporizedAsteroid(new Point(8,3), 18).point);
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,5),'#'));
        assertEquals(new Point(5,1), map.getVaporizedAsteroid(new Point(8,3), 27).point);
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,4),'#'));
        assertEquals(new Point(11,12), map.getVaporizedAsteroid(new Point(11,13), 1).point);
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,4),'#'));
        assertEquals(new Point(10,16), map.getVaporizedAsteroid(new Point(11,13), 100).point);
        map = new AsteroidsMap(InputUtil.convertInputAsCoordinates(InputUtil.readInputSampleAsString(10,4),'#'));
        assertEquals(new Point(8,2), map.getVaporizedAsteroid(new Point(11,13), 200).point);
        // solutions (backwards compatibility)
        AsteroidsMap.Location loc = Day10.part1();
        assertEquals(334, loc.visibleAsteroids);
        assertEquals(1119, Day10.part2(loc.point));
    }

}