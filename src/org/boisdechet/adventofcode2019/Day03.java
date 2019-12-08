package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.Segment;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Solver for 2019 Day 3
 * https://adventofcode.com/2019/day/3
 */
public class Day03 {

    private static Segment[] convertToSegment(String pathstr) {
        String[] moves = pathstr.split(",");
        Segment[] path = new Segment[moves.length];
        Point curPoint = new Point(0,0);
        for(int i=0; i<moves.length; i++) {
            path[i] = Segment.newSegment(curPoint, moves[i]);
            curPoint = path[i].getTo();
            Log.d(String.format("Path #%d: %s", i, path[i]));
        }
        return path;
    }

    protected static long distancePart1(String path1str, String path2str) {
        // convert to coordinates
        Segment[] path1 = convertToSegment(path1str);
        Segment[] path2 = convertToSegment(path2str);
        // find all intersections, keep nearest (manhattan distance from origin)
        Point nearest = null;
        for(int i=0; i<path1.length; i++) {
            for(int j=0; j<path2.length; j++) {
                Point cross = path1[i].cross(path2[j]);
                if(cross != null) {
                    Log.d(String.format("Cross found at %s", cross.toString()));
                    int dist = cross.manhattanDistance(0,0);
                    if(nearest == null || nearest.manhattanDistance(0,0) > dist) {
                        nearest = cross;
                    }
                }
            }
        }
        if(nearest == null) {
            throw new IllegalStateException("No cross found!");
        }
        Log.i(String.format("Nearest cross found at %s", nearest.toString()));
        return nearest.manhattanDistance(0, 0);
    }


    protected static long distancePart2(String path1str, String path2str) {
        // convert to coordinates
        Segment[] path1 = convertToSegment(path1str);
        Segment[] path2 = convertToSegment(path2str);
        // find all intersections, keep nearest (in distance)
        Point nearest = null;
        int minDist = 0;

        int distPath1 = 0;
        for(int i=0; i<path1.length; i++) {
            int distPath2 = 0;
            for(int j=0; j<path2.length; j++) {
                Point cross = path1[i].cross(path2[j]);
                if(cross != null) {
                    Log.d(String.format("Cross found at %s", cross.toString()));
                    // compute distance from last point to cross
                    Point lastVisited1 = i > 0 ? path1[i-1].getTo() : new Point(0,0);
                    Point lastVisited2 = j > 0 ? path2[j-1].getTo() : new Point(0,0);
                    int dist = distPath1 + cross.manhattanDistance(lastVisited1.x,lastVisited1.y) +
                                    distPath2 + cross.manhattanDistance(lastVisited2.x,lastVisited2.y);
                    if(nearest == null || minDist > dist) {
                        nearest = cross;
                        minDist = dist;
                    }
                }
                distPath2 += path2[j].getLength();
            }
            distPath1 += path1[i].getLength();
        }
        if(nearest == null) {
            throw new IllegalStateException("No cross found!");
        }
        Log.i(String.format("Nearest cross found at %s", nearest.toString()));
        return minDist;
    }

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        BufferedReader input = InputUtil.readInput(3, true);
        String path1 = input.readLine();
        String path2 = input.readLine();
        return distancePart1(path1, path2);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        BufferedReader input = InputUtil.readInput(3, true); // same input
        String path1 = input.readLine();
        String path2 = input.readLine();
        return distancePart2(path1, path2);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Manhattan distance is: %d", part1()));
            Log.i(String.format("Minimum distance is: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
    }
}
