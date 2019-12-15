package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.fuel.Reaction;
import org.boisdechet.adventofcode2019.fuel.Reactions;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Solver for 2019 Day 14
 * https://adventofcode.com/2019/day/14
 */
public class Day14 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        Reactions r = new Reactions(InputUtil.readInputAsReactions(14, true));
        Map<String, Reaction.Chemical> stock = new HashMap<>();
        return r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock);
    }

    /**
     * Part 2
     */
    public static long part2() throws IOException {
        Reactions r = new Reactions(InputUtil.readInputAsReactions(14, true));
        Map<String, Reaction.Chemical> stock = new HashMap<>();
        return r.getMaxFuelForOre(1000000000000L);
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Minimum amount of ORE required to produce exactly 1 FUEL: %d", part1()));
            Log.i(String.format("Maximum amount of FUEL for 1 trillion ORE: %d", part2()));
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
            exc.printStackTrace();
        }
        Log.bye();
    }
}
