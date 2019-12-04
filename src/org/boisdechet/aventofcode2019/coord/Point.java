package org.boisdechet.aventofcode2019.coord;

public class Point {
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
}
