package org.boisdechet.adventofcode2019.utils;

import org.boisdechet.adventofcode2019.droid.Droid;

import java.util.ArrayList;
import java.util.List;

public class PermutationSubsetTest {

    public static void main(String[] args) {

        int k=3;

        // create original array
        ArrayList<String> elements = new ArrayList<> ();
        for (int i=0; i < Droid.ITEMS.length; i ++){
            elements.add(Droid.ITEMS[i]);
        }
        List<List<String>> list = PermutationsSubset.choose(elements, k, true);
        for(List comb : list) {
            System.out.println(comb);
        }

    }
}
