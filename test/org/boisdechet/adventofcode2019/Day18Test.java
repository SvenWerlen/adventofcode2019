package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.coord.Vault;
import org.boisdechet.adventofcode2019.coord.VaultTooSlow;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day18Test {

    @Test
    public void examples() throws Exception {
        // part I
        assertEquals(8, new Vault(InputUtil.readSampleAsString(18, 0)).minStepsToCollectAllKeys());
        assertEquals(86, new Vault(InputUtil.readSampleAsString(18, 1)).minStepsToCollectAllKeys());
        assertEquals(132, new Vault(InputUtil.readSampleAsString(18, 2)).minStepsToCollectAllKeys());
        assertEquals(136, new Vault(InputUtil.readSampleAsString(18, 3)).minStepsToCollectAllKeys());
        assertEquals(81, new Vault(InputUtil.readSampleAsString(18, 4)).minStepsToCollectAllKeys());
        // part II
        assertEquals(8, new Vault(InputUtil.readSampleAsString(18, 5), true).minStepsToCollectAllKeys());
        assertEquals(24, new Vault(InputUtil.readSampleAsString(18, 6), true).minStepsToCollectAllKeys());
        assertEquals(32, new Vault(InputUtil.readSampleAsString(18, 7), true).minStepsToCollectAllKeys());
        assertEquals(72, new Vault(InputUtil.readSampleAsString(18, 8), true).minStepsToCollectAllKeys(false)); // speedup doesn't work here
        // solutions (backwards compatibility)
        assertEquals(4676, Day18.part1());
        assertEquals(2066, Day18.part2());
    }

}