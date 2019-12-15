package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Moon;
import org.boisdechet.adventofcode2019.coord.Moons;
import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.opcode.ArcadeCabinet;
import org.boisdechet.adventofcode2019.opcode.EmergencyHull;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.opcode.Robot;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Solver for 2019 Day 13
 * https://adventofcode.com/2019/day/13
 */
public class Day13 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        ArcadeCabinet arcade = new ArcadeCabinet(new OpCodeMachine(InputUtil.convertToLongArray(InputUtil.readInputAsString(13, true))));
        List<ArcadeCabinet.Output> outputs = arcade.getOutputsTillExit();
        Map<Point, Integer> map = new HashMap<>();
        for(ArcadeCabinet.Output out : outputs) {
            if(out.tileType == ArcadeCabinet.Output.TILE_BLOCK) {
                map.put(out.coord, out.tileType);
            }
        }
        return map.size();
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        long[] code = InputUtil.convertToLongArray(InputUtil.readInputAsString(13, true));
        // play free
        code[0] = 2;
        // start game
        ArcadeCabinet arcade = new ArcadeCabinet(new OpCodeMachine(code));
        return arcade.playTillExit();
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Block tiles on the screen at the end of the game: %d", part1()));
            Log.i(String.format("Score after the last block is broken: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
