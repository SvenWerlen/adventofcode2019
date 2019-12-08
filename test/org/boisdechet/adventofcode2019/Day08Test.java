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
        // part I
        SpaceImage image = new SpaceImage(3,2, InputUtil.convertToIntArrayNoSep("123456789012"));
        assertTrue(image.isValid());
        assertEquals(2, image.getLayersCount());
        assertEquals(1, image.getColorCountForLayer(5,0));
        image = new SpaceImage(2,2,InputUtil.convertToIntArrayNoSep("0222112222120000"));
        // part II
        String result = "Image:\n" +
                "01\n" +
                "10\n";
        assertEquals(result, image.toString());
        // solutions (backwards compatibility)
        assertEquals(1862, Day08.part1());
        result = "Image:\n" +
                " ##   ##  ###  #  # #    \n" +
                "#  # #  # #  # #  # #    \n" +
                "#    #    #  # #### #    \n" +
                "# ## #    ###  #  # #    \n" +
                "#  # #  # #    #  # #    \n" +
                " ###  ##  #    #  # #### \n";
        assertEquals(result, Day08.part2());
    }

}