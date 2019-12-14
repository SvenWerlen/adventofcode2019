package org.boisdechet.adventofcode2019.utils;

/**
 * From: https://www.baeldung.com/java-least-common-multiple
 */
public class MathUtil {

    public static long leastCommonMultiple(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    public static long leastCommonMultiple(long number1, long number2, long number3) {
        return leastCommonMultiple(leastCommonMultiple(number1, number2), number3);
    }
}