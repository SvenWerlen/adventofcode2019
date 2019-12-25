package org.boisdechet.adventofcode2019.shuffle;

import java.math.BigInteger;
import java.util.Objects;

/**
 * From https://github.com/MagmaMcFry/AdventOfCode2019/blob/master/src/main/java/aoc19/days/Day22Solver.java
 * I have not been able to understand the math behind it :-(
 */
public class Shuffle {
    long numCards;
    long factor;
    long offset;

    public Shuffle(long numCards, long factor, long offset) {
        this.numCards = numCards;
        this.factor = ((factor % numCards) + numCards) % numCards;
        this.offset = ((offset % numCards) + numCards) % numCards;
    }

    public static Shuffle cut(long numCards, long offset) {
        return new Shuffle(numCards, 1, -offset);
    }

    public static Shuffle dealIncrement(long numCards, long factor) {
        return new Shuffle(numCards, factor, 0);
    }

    public static Shuffle newStack(long numCards) {
        return new Shuffle(numCards, -1, -1);
    }

    private static Shuffle identity(long numCards) {
        return new Shuffle(numCards, 1, 0);
    }

    public static Shuffle get(long numCards, String... spec) {
        Shuffle result = Shuffle.identity(numCards);
        for (String s : spec) {
            if (s.startsWith("deal with increment ")) {
                result = result.then(dealIncrement(numCards, Long.parseLong(s.substring(20))));
            } else if (s.startsWith("cut ")) {
                result = result.then(cut(numCards, Long.parseLong(s.substring(4))));
            } else if (s.equals("deal into new stack")) {
                result = result.then(newStack(numCards));
            }
        }
        return result;
    }

    public Shuffle then(Shuffle after) {
        if (this.numCards != after.numCards)
            throw new IllegalArgumentException("Can't compose shuffles with different numbers of cards");
        return new Shuffle(
                numCards,
                multMod(this.factor, after.factor, numCards),
                multMod(after.factor, this.offset, numCards) + after.offset
        );
    }

    public long apply(long card) {
        return (multMod(card, factor, numCards) + offset) % numCards;
    }

    public Shuffle invert() {
        long invFactor = invertMod(factor, numCards);
        return new Shuffle(numCards, invFactor, -multMod(invFactor, offset, numCards));
    }

    public Shuffle repeat(long times) {
        Shuffle ans = Shuffle.identity(numCards);
        long n = 1;
        Shuffle thisToN = this;
        while (n <= times) {
            if ((n & times) != 0) {
                ans = ans.then(thisToN);
            }
            n *= 2;
            thisToN = thisToN.then(thisToN);
        }
        return ans;
    }

    public boolean isIdentity() {
        return factor == 1 && offset == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shuffle shuffle = (Shuffle) o;
        return numCards == shuffle.numCards &&
                offset == shuffle.offset &&
                factor == shuffle.factor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numCards, offset, factor);
    }

    public static long multMod(long a, long b, long mod) {
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigB = BigInteger.valueOf(b);
        BigInteger bigMod = BigInteger.valueOf(mod);
        return bigA.multiply(bigB).mod(bigMod).longValueExact();
    }

    public static long invertMod(long val, long mod) {
        BigInteger bigVal = BigInteger.valueOf(val);
        BigInteger bigMod = BigInteger.valueOf(mod);
        return bigVal.modInverse(bigMod).longValueExact();
    }
}
