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

    public static long getStepsCountForFirstMatchSlow(List<Moon> moonsList, int moonIdx) {
        Moon moon = moonsList.get(moonIdx);
        List<Moon> moons = Moons.cloneMoons(moonsList);
        long steps = 0;
        while(true) {
            int count = 1;
            Moons.step(moons);
            while(!moons.get(moonIdx).equals(moon)) {
                Moons.step(moons);
                count++;
            }
            Log.d("Loop after " + count + " steps");
            steps += count;
            if(count == 1) {
                break;
            }
        }
        return steps;
    }

    public static long getStepsCountForFirstMatchSlow(List<Moon> moons) {
        long lcm = 1;
        for(int i=0; i<moons.size(); i++) {
            long steps = getStepsCountForFirstMatchSlow(moons, i);
            Log.i(String.format("Cycle for moon #%d is %d steps", i+1, steps));
            lcm = MathUtil.leastCommonMultiple(lcm, steps);
        }
        return lcm;
    }


    public static long getStepsCountForFirstMatch(List<Moon> moonsList, int moonIdx) {
        Moon moon = moonsList.get(moonIdx);
        List<Moon> moons = Moons.cloneMoons(moonsList);
        long stepsX = 0;
        while(true) {
            int count = 1;
            Moons.step(moons);
            while(moons.get(moonIdx).x != moon.x) {
                Moons.step(moons);
                count++;
            }
            Log.d("Loop after " + count + " steps");
            stepsX += count;
            if(count == 1) {
                break;
            }
        }
        moons = Moons.cloneMoons(moonsList);
        long stepsY = 0;
        while(true) {
            int count = 1;
            Moons.step(moons);
            while(moons.get(moonIdx).y != moon.y) {
                Moons.step(moons);
                count++;
            }
            Log.d("Loop after " + count + " steps");
            stepsY += count;
            if(count == 1) {
                break;
            }
        }
        moons = Moons.cloneMoons(moonsList);
        long stepsZ = 0;
        while(true) {
            int count = 1;
            Moons.step(moons);
            while(moons.get(moonIdx).z != moon.z) {
                Moons.step(moons);
                count++;
            }
            Log.d("Loop after " + count + " steps");
            stepsZ += count;
            if(count == 1) {
                break;
            }
        }
        Log.d(String.format("Loop after respectively (%d,%d,%d)", stepsX, stepsY, stepsZ));
        return MathUtil.leastCommonMultiple(stepsX, stepsY, stepsZ);
    }

    public static long getStepsCountForFirstMatch(List<Moon> moons) {
        long lcm = 1;
        for(int i=0; i<moons.size(); i++) {
            long steps = getStepsCountForFirstMatch(moons, i);
            Log.i(String.format("Cycle for moon #%d is %d steps", i+1, steps));
            lcm = MathUtil.leastCommonMultiple(lcm, steps);
        }
        return lcm;
    }
}
