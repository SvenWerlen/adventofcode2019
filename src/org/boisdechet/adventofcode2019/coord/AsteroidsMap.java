package org.boisdechet.adventofcode2019.coord;


import org.boisdechet.adventofcode2019.utils.Log;

import java.util.*;
import java.util.stream.Stream;

public class AsteroidsMap {

    private static final double ROUNDING = 0.001d;

    private List<Point> points;
    private int maxX = 0;
    private int maxY = 0;


    // POJO
    public class Location {
        public Point point;
        public int visibleAsteroids;
        public double angle;
    }

    public AsteroidsMap(List<Point> points) {
        this.points = points;
        for(Point p : points) {
            if(p.x > maxX) {
                maxX = p.x;
            }
            if(p.y > maxY) {
                maxY = p.y;
            }
        }
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

    public static double getAngle(Point from, Point to) {
        double angle = Math.atan2(to.y-from.y, to.x-from.x) + Math.PI/2;
        return angle >= 0 ? angle : 2*Math.PI+angle;
    }

    public Location getBestLocation() {
        Location loc = new Location();
        Map<Point, Set<Point>> lineofsight = new HashMap<>();
        // initialize
        for(Point from : points) {
            lineofsight.put(from, new HashSet<>());
        }
        // find all visible asteroids
        for(Point from : points) {
            for(Point to : points) {
                if(!from.equals(to) && !lineofsight.get(from).contains(to) && hasLineOfSight(from, to)) {
                    lineofsight.get(from).add(to);
                    lineofsight.get(to).add(from);
                }
            }
        }
        // find best location
        for(Point from : points) {
            int count = lineofsight.get(from).size();
            if(loc.point == null || loc.visibleAsteroids < count) {
                loc.point = from;
                loc.visibleAsteroids = count;
            }
        }
        return loc;
    }

    public Location getNextAsteroid(Point from, double startAngle) {
        Location loc = new Location();
        for(Point to : points) {
            double angle = getAngle(from, to);
            if(angle < 0) {
                throw new IllegalStateException(String.format("Angle not supposed to be negative! %f", angle));
            }
            if(angle >= startAngle && (loc.point == null || angle < loc.angle) && hasLineOfSight(from, to)) {
                loc.point = to;
                loc.angle = angle;
            }
        }
        // not found
        if(loc.point == null) {
            if(startAngle == 0) {
                throw new IllegalStateException(String.format("Next asteroid couldn't be found with %d asteroid remaining\n%s", points.size(), toString()));
            } else {
                return getNextAsteroid(from, 0d);
            }
        } else {
            return loc;
        }
    }

    public Location getVaporizedAsteroid(Point from, int nth) {
        Location loc = new Location();
        loc.angle = -ROUNDING;
        int i = 0;
        while(i != nth) {
            // check
            if(points.size() == 0) {
                throw new IllegalStateException("No asteroid to vaporize any more!");
            }
            loc = getNextAsteroid(from, loc.angle + ROUNDING);
            // vaporize
            points.remove(loc.point);
            i++;
        }
        return loc;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for(int y = 0; y<=maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if(points.contains(new Point(x,y))) {
                    buf.append('#');
                } else {
                    buf.append('.');
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
