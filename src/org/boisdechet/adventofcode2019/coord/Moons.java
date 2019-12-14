package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.utils.Log;
import org.boisdechet.adventofcode2019.utils.MathUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Moons {

    public static List<Moon> cloneMoons(List<Moon> list) {
        List<Moon> clone = new ArrayList<>();
        for(Moon m : list) {
            clone.add(new Moon(m));
        }
        return clone;
    }

    public static void step(List<Moon> moons) {
        // apply gravity
        for(Moon m : moons) {
            m.applyGravity(moons);
        }
        // apply velocity
        for(Moon m : moons) {
            m.applyVelocity();
        }
    }

    public static int getTotalEnergy(List<Moon> moons) {
        int total = 0;
        for(Moon m : moons) {
            total += m.getTotalEnergy();
        }
        return total;
    }

    public static long getStepsCountForFirstMatchVerySlow(List<Moon> moons) {
        moons = Moons.cloneMoons(moons);
        long count = 0;
        Set<Integer> match = new HashSet<>();
        match.add(Moons.hash(moons));
        while(true) {
            count++;
            Moons.step(moons);
            int hash = Moons.hash(moons);
            if(match.contains(hash)) {
                return count;
            }
        }
    }

    public static int hash(List<Moon> moons) {
        StringBuffer buf = new StringBuffer();
        for(Moon m : moons) {
            buf.append(m.x).append(':').append(m.y).append(':').append(m.z).append(':');
            buf.append(m.vx).append(':').append(m.vy).append(':').append(m.vz);
            buf.append(',');
        }
        return buf.toString().hashCode();
    }

    private static int hashX(List<Moon> moons) {
        StringBuffer buf = new StringBuffer();
        for(Moon m : moons) {
            buf.append(m.x).append(':').append(m.vx).append(',');
        }
        return buf.toString().hashCode();
    }

    private static int hashY(List<Moon> moons) {
        StringBuffer buf = new StringBuffer();
        for(Moon m : moons) {
            buf.append(m.y).append(':').append(m.vy).append(',');
        }
        return buf.toString().hashCode();
    }

    private static int hashZ(List<Moon> moons) {
        StringBuffer buf = new StringBuffer();
        for(Moon m : moons) {
            buf.append(m.z).append(':').append(m.vz).append(',');
        }
        return buf.toString().hashCode();
    }

    private static long getStepsCountForFirstMatch(List<Moon> moons, int coord) {
        moons = Moons.cloneMoons(moons);
        long count = 0;
        Set<Integer> match = new HashSet<>();
        int hash = coord == 0 ? hashX(moons) : coord == 1 ? hashY(moons) : hashZ(moons);
        match.add(hash);
        while(true) {
            count++;
            Moons.step(moons);
            hash = coord == 0 ? hashX(moons) : coord == 1 ? hashY(moons) : hashZ(moons);
            if(match.contains(hash)) {
                return count;
            }
        }
    }

    public static long getStepsCountForFirstMatch(List<Moon> moons) {
        long countX = getStepsCountForFirstMatch(Moons.cloneMoons(moons), 0);
        long countY = getStepsCountForFirstMatch(Moons.cloneMoons(moons), 1);
        long countZ = getStepsCountForFirstMatch(Moons.cloneMoons(moons), 2);
        return MathUtil.leastCommonMultiple(countX, countY, countZ);
    }
}
