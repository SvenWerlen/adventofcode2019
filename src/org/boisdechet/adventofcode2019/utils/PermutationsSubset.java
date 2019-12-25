package org.boisdechet.adventofcode2019.utils;

import java.util.*;

/**
 * Adapted from: https://stackoverflow.com/questions/44949030/print-all-possible-permutations-of-r-elements-in-a-given-integer-array-of-size-n?answertab=votes#tab-top
 */
public class PermutationsSubset {


    // a is the original array
    // k is the number of elements in each permutation
    public static List<List<String>> choose(List<String> a, int k, boolean removeDuplicates) {
        List<List<String>> allPermutations = new ArrayList<>();
        enumerate(a, a.size(), k, allPermutations);
        if(removeDuplicates) {
            List<List<String>> filtered = new ArrayList<>();
            Set<String> combExists = new HashSet<>();
            for(List<String> comb : allPermutations) {
                Collections.sort(comb);
                StringBuffer buf = new StringBuffer();
                for(String s : comb) {
                    buf.append(s);
                }
                if(!combExists.contains(buf.toString())) {
                    filtered.add(comb);
                    combExists.add(buf.toString());
                }
            }
            allPermutations = filtered;
        }
        return allPermutations;
    }

    // a is the original array
    // n is the array size
    // k is the number of elements in each permutation
    // allPermutations is all different permutations
    private static void enumerate(List<String> a, int n, int k, List<List<String>> allPermutations) {
        if (k == 0) {
            List<String> singlePermutation = new ArrayList<String>();
            for (int i = n; i < a.size(); i++){
                singlePermutation.add(a.get(i));
            }
            allPermutations.add(singlePermutation);
            return;
        }

        for (int i = 0; i < n; i++) {
            swap(a, i, n-1);
            enumerate(a, n-1, k-1, allPermutations);
            swap(a, i, n-1);
        }
    }

    // helper function that swaps a.get(i) and a.get(j)
    public static void swap(List<String> a, int i, int j) {
        String temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

}