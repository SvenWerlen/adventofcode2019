package org.boisdechet.adventofcode2019.coord;

public class Point implements Cloneable {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int manhattanDistance(int x, int y) {
        return Math.abs(this.x-x) + Math.abs(this.y-y);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Point) {
            Point p = (Point)o;
            return x == p.x && y == p.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return String.format("%d,%d",x,y).hashCode();
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }
}
