package org.boisdechet.adventofcode2019.utils;

import java.util.Arrays;

public class Log {

    public static boolean DEBUG = false;
    private static final String WELCOME_MESSAGE = "Avent of code 2019";
    private static final String SEPARATOR       = "==================";

    private static void printMessage(String text) {
        System.out.println(text);
    }

    public static void welcome() {
        printMessage(SEPARATOR);
        printMessage(WELCOME_MESSAGE);
        printMessage(SEPARATOR);
    }

    public static void i(String text) {
        printMessage(text);
    }

    public static void i(int[] values) {
        printMessage(Arrays.toString(values));
    }

    public static void i(long[] values) {
        printMessage(Arrays.toString(values));
    }

    public static void w(String text) {
        printMessage(String.format("[W] %s", text));
    }

    public static void d(String text) {
        if(DEBUG) {
            printMessage(String.format("[D] %s", text));
        }
    }

    public static void d(int[] values) {
        if(DEBUG) {
            printMessage(String.format("[D] %s", Arrays.toString(values)));
        }
    }

    public static void d(long[] values) {
        if(DEBUG) {
            printMessage(String.format("[D] %s", Arrays.toString(values)));
        }
    }
}
