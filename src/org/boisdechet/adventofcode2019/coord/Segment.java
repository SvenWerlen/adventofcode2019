package org.boisdechet.adventofcode2019.coord;

import org.boisdechet.adventofcode2019.utils.Log;

public class Segment {

    public static final int DIRECTION_HORIZONTAL = 0;
    public static final int DIRECTION_VERTICAL = 1;

    protected Point from, to;
    protected int direction;

    public Segment(Point fromPoint, Point toPoint) {
        from = fromPoint;
        to = toPoint;
        if(fromPoint.x == toPoint.x) {
            direction = DIRECTION_VERTICAL;
        } else if (fromPoint.y == toPoint.y) {
            direction = DIRECTION_HORIZONTAL;
        } else {
            throw new IllegalStateException("Segment only supports horizontal/vertical directions!");
        }
    }

    public static Segment newSegment(Point fromPoint, String path) {
        int dist = Integer.parseInt(path.substring(1));
        switch(path.charAt(0)) {
            case 'U': return new Segment(fromPoint, new Point(fromPoint.x, fromPoint.y + dist));
            case 'R': return new Segment(fromPoint, new Point(fromPoint.x + dist, fromPoint.y));
            case 'D': return new Segment(fromPoint, new Point(fromPoint.x, fromPoint.y - dist));
            case 'L': return new Segment(fromPoint, new Point(fromPoint.x - dist, fromPoint.y));
            default: throw new IllegalStateException(String.format("Invalid direction %s",path));
        }
    }

    public Point getFrom() { return from; }
    public Point getTo() { return to; }

    public int getLength() {
        return Math.abs(from.x-to.x) + Math.abs(from.y-to.y);
    }

    /**
     * Checks if the two segments intersects
     * @param seg second segment
     * @return intersection point or null (if no intersection)
     */
    public Point cross(Segment seg) {
        Point cross = null;
        // not parallel
        if(this.direction != seg.direction) {

            // first segment is horizontal
            if (direction == DIRECTION_HORIZONTAL) {
                if(seg.from.x >= Math.min(from.x,to.x) && seg.from.x <= Math.max(from.x,to.x) &&
                        from.y >= Math.min(seg.from.y,seg.to.y) && from.y <= Math.max(seg.from.y,seg.to.y)) {
                    cross = new Point(seg.from.x, from.y);
                }
            }
            // first segment is vertical
            else if (direction == DIRECTION_VERTICAL) {
                if(from.x >= Math.min(seg.from.x,seg.to.x) && from.x <= Math.max(seg.from.x,seg.to.x) &&
                        seg.from.y >= Math.min(from.y,to.y) && seg.from.y <= Math.max(from.y,to.y)) {
                    cross = new Point(from.x, seg.from.y);
                }
            }
            // no intersection
            else {
                cross = null;
            }
        }
        // ignore origin ;-)
        Log.d(String.format("Test cross %s - %s => %s", this.toString(), seg.toString(), cross == null ? "no cross" : cross.toString()));
        return cross != null && cross.manhattanDistance(0,0) > 0 ? cross : null;
    }

    @Override
    public String toString() {
        return String.format("S{%s,%s}", from.toString(), to.toString());
    }
}
