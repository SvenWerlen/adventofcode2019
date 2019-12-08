package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.image.SpaceImage;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Day08Test {

    @Test
    public void examples() throws Exception {
        // samples
        SpaceImage image = new SpaceImage(3,2, InputUtil.convertToIntArrayNoSep("123456789012"));
        Log.i(image.toString());
        assertTrue(image.isValid());
        assertEquals(2, image.getLayersCount());
        assertEquals(1, image.getColorCountForLayer(5,0));
        image = new SpaceImage(2,2,InputUtil.convertToIntArrayNoSep("0222112222120000"));
        Log.i(image.toString());
        // part I
        // part II
        // solutions (backwards compatibility)
        // not applicable!
    }

}