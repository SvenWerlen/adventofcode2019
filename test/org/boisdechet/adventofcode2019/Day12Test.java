package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Moon;
import org.boisdechet.adventofcode2019.coord.Moons;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day12Test {

    @Test
    public void moon() throws Exception {
        Moon m = new Moon("<x=-1, y=0, z=2>");
        assertEquals(-1, m.x);
        assertEquals(0, m.y);
        assertEquals(2, m.z);
    }

    @Test
    public void examples() throws Exception {
        // part I
        List<Moon> list = new ArrayList<Moon>();
        Moon m1 = new Moon("<x=-1, y=0, z=2>");
        Moon m2 = new Moon("<x=2, y=-10, z=-7>");
        Moon m3 = new Moon("<x=4, y=-8, z=8>");
        Moon m4 = new Moon("<x=3, y=5, z=-1>");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        for(int i=0; i<10; i++) {
            Moons.step(list);
        }
        assertEquals("Moon (2,1,-3), velocity (-3,-2,1)", m1.toString());
        assertEquals("Moon (1,-8,0), velocity (-1,1,3)", m2.toString());
        assertEquals("Moon (3,-6,1), velocity (3,2,-3)", m3.toString());
        assertEquals("Moon (2,0,4), velocity (1,-1,-1)", m4.toString());
        assertEquals(6, m1.getPotentialEnergy());
        assertEquals(6, m1.getKineticEnergy());
        assertEquals(36, m1.getTotalEnergy());
        assertEquals(179, Moons.getTotalEnergy(list));
        // part II
        list = new ArrayList<Moon>();
        m1 = new Moon("<x=-1, y=0, z=2>");
        m2 = new Moon("<x=2, y=-10, z=-7>");
        m3 = new Moon("<x=4, y=-8, z=8>");
        m4 = new Moon("<x=3, y=5, z=-1>");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        assertEquals(2772, Moons.getStepsCountForFirstMatchVerySlow(Moons.cloneMoons(list)));
        assertEquals(2772, Moons.getStepsCountForFirstMatch(Moons.cloneMoons(list)));
        list = new ArrayList<Moon>();
        m1 = new Moon("<x=-8, y=-10, z=0>");
        m2 = new Moon("<x=5, y=5, z=10>");
        m3 = new Moon("<x=2, y=-7, z=3>");
        m4 = new Moon("<x=9, y=-8, z=-3>");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        assertEquals(4686774924L, Moons.getStepsCountForFirstMatch(Moons.cloneMoons(list)));
        // solutions (backwards compatibility)
        assertEquals(6227, Day12.part1());
        assertEquals(331346071640472L, Day12.part2());
    }

}