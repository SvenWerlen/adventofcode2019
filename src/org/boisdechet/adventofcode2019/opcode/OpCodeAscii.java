package org.boisdechet.adventofcode2019.opcode;

public class OpCodeAscii {

    public static int[] getInput(String input) {
        int[] result = new int[input.length()];
        for(int i=0; i<input.length(); i++) {
            result[i] = (int)input.charAt(i);
        }
        return result;
    }
}
