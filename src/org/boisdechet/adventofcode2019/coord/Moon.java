package org.boisdechet.adventofcode2019.coord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Moon {

    public int x,y,z;
    public int vx,vy,vz;

    public Moon() {}
    public Moon(String init) {
        Pattern p = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
        Matcher m = p.matcher(init);
        if(m.matches()) {
            x = Integer.parseInt(m.group(1));
            y = Integer.parseInt(m.group(2));
            z = Integer.parseInt(m.group(3));
        } else {
            throw new IllegalStateException(String.format("Illegal moon format: %s", init));
        }
    }

    public Moon(Moon copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
        this.vx = copy.vx;
        this.vy = copy.vy;
        this.vz = copy.vz;
    }

    private static int velocityInc(int pos1, int pos2) {
        if(pos1 > pos2) {
            return -1;
        } else if(pos2 > pos1) {
            return 1;
        } else {
            return 0;
        }
    }

    public void applyGravity(List<Moon> moons) {
        for(Moon m : moons) {
            // ignore itself
            if(m == this) {
                continue;
            }
            this.vx += velocityInc(this.x, m.x);
            this.vy += velocityInc(this.y, m.y);
            this.vz += velocityInc(this.z, m.z);
        }
    }

    public void applyVelocity() {
        this.x += this.vx;
        this.y += this.vy;
        this.z += this.vz;
    }

    public int getPotentialEnergy() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    public int getKineticEnergy() {
        return Math.abs(vx) + Math.abs(vy) + Math.abs(vz);
    }

    public int getTotalEnergy() {
        return getPotentialEnergy() * getKineticEnergy();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Moon) {
            Moon moon = (Moon)obj;
            return this.x == moon.x && this.y == moon.y && this.z == moon.z;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Moon (%d,%d,%d), velocity (%d,%d,%d)",x,y,z,vx,vy,vz);
    }
}
