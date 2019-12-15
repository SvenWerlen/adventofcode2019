package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.image.SpaceImage;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Solver for 2019 Day 8
 * https://adventofcode.com/2019/day/8
 */
public class Day08 {

    /**
     * Part 1
     */
    public static long part1() throws IOException {
        SpaceImage image = new SpaceImage(25, 6, InputUtil.convertToIntArrayNoSep(InputUtil.readInputAsString(8, true)));
        if(!image.isValid()) {
            throw new IllegalStateException("Invalid image input!");
        }
        // find layers with min '0'
        int[] layers = image.getColorCountByLayer(0);
        int minCount = image.getHeight() * image.getWidth();
        int minLayer = -1;
        for(int i=0; i<layers.length; i++) {
            if(minLayer < 0 || minCount > layers[i]) {
                minCount = layers[i];
                minLayer = i;
            }
        }
        Log.d(String.format("Min 0s is %d for layer %d", minCount, minLayer));
        // compute result
        int c1 = image.getColorCountForLayer(1, minLayer);
        int c2 = image.getColorCountForLayer(2, minLayer);
        return c1 * c2;
    }

    /**
     * Part 2
     */
    public static String part2() throws IOException {
        SpaceImage image = new SpaceImage(25, 6, InputUtil.convertToIntArrayNoSep(InputUtil.readInputAsString(8, true)));
        return image.toString(Map.of(1, '#'));
    }

    public static void main(String[] args) {
        Log.welcome();
        try {
            Log.i(String.format("Number of 1 digits multiplied by the number of 2 digits: %d", part1()));
            Log.i(part2());
        } catch(Exception exc) {
            Log.w(String.format("Error during execution: %s", exc.getMessage()));
        }
        Log.bye();
    }
}
