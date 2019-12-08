package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class Day03Test {

    @Test
    public void Point() {
        assertEquals(6, (new Point(0,0)).manhattanDistance(3,3));
        assertEquals(5, (new Point(2,0)).manhattanDistance(0,3));
    }

    @Test
    public void Segment() {
        Segment seg1 = new Segment(new Point(2,2), new Point(12, 2));
        Segment seg2 = new Segment(new Point(4,6), new Point(4, 0));
        Point cross = seg1.cross(seg2);
        assertNotNull(cross);
        assertEquals(4, cross.x);
        assertEquals(2, cross.y);
        assertEquals(10, seg1.getLength());
        assertEquals(6, seg2.getLength());
    }

    @Test
    public void examples() throws Exception {
        //Log.DEBUG = true;
        // part 1
        assertEquals(6, Day03.distancePart1("R8,U5,L5,D3", "U7,R6,D4,L4"));
        assertEquals(159, Day03.distancePart1("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83"));
        assertEquals(135, Day03.distancePart1("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"));
        // part 2
        assertEquals(30, Day03.distancePart2("R8,U5,L5,D3", "U7,R6,D4,L4"));
        assertEquals(610, Day03.distancePart2("R75,D30,R83,U83,L12,D49,R71,U7,L72","U62,R66,U55,R34,D71,R55,D58,R83"));
        assertEquals(410, Day03.distancePart2("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51","U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"));
        // solutions (backwards compatibility)
        assertEquals(232, Day03.part1());
        assertEquals(6084, Day03.part2());
    }

}