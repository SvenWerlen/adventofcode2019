package org.boisdechet.adventofcode2019.shuffle;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuffleTechniques {

    public static int[] dealIntoStack(int[] cards) {
        int[] result = new int[cards.length];
        for(int i=0; i<cards.length; i++) {
            result[cards.length-i-1] = cards[i];
        }
        return result;
    }

    public static int[] cut(int[] cards, int count) {
        if(count < 0) {
            count = cards.length + count;
        }
        int[] result = new int[cards.length];
        System.arraycopy(cards, 0, result, cards.length - count, count);
        System.arraycopy(cards, count, result, 0, cards.length - count);
        return result;
    }

    public static int[] dealWithIncrement(int[] cards, int increment) {
        int[] result = new int[cards.length];
        for(int i=0; i<cards.length; i++) {
            result[(i*increment) % cards.length] = cards[i];
        }
        return result;
    }

    public static int[] shuffle(List<String> instr, int cardsCount) {
        // initialize cards
        int[] cards = new int[cardsCount];
        for(int i=0; i<cardsCount; i++) {
            cards[i] = i;
        }
        // initialize patterns
        Pattern pCut = Pattern.compile("cut (-?\\d+)");
        Pattern pDealInc = Pattern.compile("deal with increment (\\d+)");
        Matcher m;
        // apply techniques
        for(String tech : instr) {
            if(Log.DEBUG) { Log.d(String.format("Cards: %s", dumpCards(cards))); }
            if("deal into new stack".equals(tech)) {
                if(Log.DEBUG) { Log.d("Deal into new stack"); }
                cards = dealIntoStack(cards);
            } else if((m = pCut.matcher(tech)).matches()) {
                if(Log.DEBUG) { Log.d(String.format("Cut %d", Integer.parseInt(m.group(1)))); }
                cards = cut(cards, Integer.parseInt(m.group(1)));
            } else if((m = pDealInc.matcher(tech)).matches()) {
                if(Log.DEBUG) { Log.d(String.format("Deal with increment %d", Integer.parseInt(m.group(1)))); }
                cards = dealWithIncrement(cards, Integer.parseInt(m.group(1)));
            } else {
                throw new IllegalStateException(String.format("Invalid shuffle technique: %s", tech));
            }
        }
        if(Log.DEBUG) { Log.d(String.format("Cards (final): %s", dumpCards(cards))); }
        return cards;
    }

    public static String dumpCards(int[] cards) {
        StringBuffer buf = new StringBuffer(3 * cards.length);
        for(Integer c : cards) {
            buf.append(String.format("%3d", c));
        }
        return buf.toString();
    }
}
