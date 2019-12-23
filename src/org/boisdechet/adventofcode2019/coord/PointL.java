package org.boisdechet.adventofcode2019.coord;

public class PointL implements Cloneable {
    public long x, y;

    public PointL(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long manhattanDistance(int x, int y) {
        return Math.abs(this.x-x) + Math.abs(this.y-y);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PointL) {
            PointL p = (PointL)o;
            return x == p.x && y == p.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return String.format("%d,%d",x,y).hashCode();
    }

    @Override
    public PointL clone() {
        return new PointL(this.x, this.y);
    }
}
