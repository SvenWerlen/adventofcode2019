package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.fuel.Reaction;
import org.boisdechet.adventofcode2019.fuel.Reactions;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day14Test {

    @Test
    public void examples() throws Exception {
        // part I
        Reactions r = new Reactions(InputUtil.readSampleAsReactions(14, 0));
        Map<String, Reaction.Chemical> stock = new HashMap<>();
        assertEquals(31, r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 1));
        stock.clear();
        assertEquals(165, r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 2));
        stock.clear();
        assertEquals(13312, r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 3));
        stock.clear();
        assertEquals(180697, r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 4));
        stock.clear();
        assertEquals(2210736, r.getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock));
        // part II
        r = new Reactions(InputUtil.readSampleAsReactions(14, 2));
        stock.clear();
        assertEquals(82892753, r.getMaxFuelForOre(1000000000000L));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 3));
        stock.clear();
        assertEquals(5586022, r.getMaxFuelForOre(1000000000000L));
        r = new Reactions(InputUtil.readSampleAsReactions(14, 4));
        stock.clear();
        assertEquals(460664, r.getMaxFuelForOre(1000000000000L));
        // solutions (backwards compatibility)
        assertEquals(532506, Day14.part1());
        assertEquals(2595245, Day14.part2());
    }

}