package org.boisdechet.adventofcode2019;

import org.boisdechet.adventofcode2019.fft.FlawedFrequencyTransmission;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day16Test {

    @Test
    public void examples() throws Exception {
        assertEquals("48226158", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("12345678")).cleanSignal(1));
        assertEquals("34040438", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("12345678")).cleanSignal(2));
        assertEquals("03415518", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("12345678")).cleanSignal(3));
        assertEquals("01029498", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("12345678")).cleanSignal(4));
        assertEquals("24176176", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("80871224585914546619083218645595")).cleanSignal(100));
        assertEquals("73745418", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("19617804207202209144916044189917")).cleanSignal(100));
        assertEquals("52432133", new FlawedFrequencyTransmission(InputUtil.convertToDigitArray("69317163492948606335995924319873")).cleanSignal(100));
        // part II
        assertEquals("84462026", FlawedFrequencyTransmission.cleanSignal(InputUtil.convertToDigitArray("03036732577212944063491565474664"), 100, 10000, 7));
        assertEquals("78725270", FlawedFrequencyTransmission.cleanSignal(InputUtil.convertToDigitArray("02935109699940807407585447034323"), 100, 10000, 7));
        assertEquals("53553731", FlawedFrequencyTransmission.cleanSignal(InputUtil.convertToDigitArray("03081770884921959731165446850517"), 100, 10000, 7));
        // solutions (backwards compatibility)
        assertEquals("17978331", Day16.part1());
        assertEquals("19422575", Day16.part2());
    }

}