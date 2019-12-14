package org.boisdechet.adventofcode2019.coord;


import org.boisdechet.adventofcode2019.utils.Log;

import java.util.List;

public class AsteroidsMap {

    private static final double ROUNDING = 0.001d;

    List<Point> points;

    // POJO
    public class BestLocation {
        public Point point;
        public int visibleAsteroids;
    }

    public AsteroidsMap(List<Point> points) {
        this.points = points;
    }

    public boolean hasLineOfSight(Point from, Point to) {
        for(Point p : points) {
            Log.d(String.format("Is %s between %s and %s?", p, from, to));
            // ignore from/to
            if(p.equals(from) || p.equals(to)) {
                Log.d(String.format("Ignoring %s", p));
                continue;
            }
            // check if point is in zone (between from and to)
            if(((p.x >= from.x && p.x <= to.x) || (p.x >= to.x && p.x <= from.x)) &&
                    ((p.y >= from.y && p.y <= to.y) || (p.y >= to.y && p.y <= from.y))) {
                // check if "direction" is the same
                double dir1 = Math.atan2(from.y-to.y, from.x-to.x);
                double dir2 = Math.atan2(from.y-p.y, from.x-p.x);
                Log.d(String.format("Direction difference is %f", (dir1-dir2)));
                if(Math.abs(dir1-dir2) < ROUNDING) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getVisibleAsteroids(Point from) {
        int count = 0;
        for(Point to : points) {
            if(!from.equals(to) && hasLineOfSight(from, to)) {
                count++;
            }
        }
        return count;
    }

    public BestLocation getBestLocation() {
        BestLocation loc = new BestLocation();
        for(Point from : points) {
            int count = getVisibleAsteroids(from);
            if(loc.point == null || loc.visibleAsteroids < count) {
                loc.point = from;
                loc.visibleAsteroids = count;
            }
        }
        return loc;
    }
}
