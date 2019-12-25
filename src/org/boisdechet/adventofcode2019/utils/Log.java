package org.boisdechet.adventofcode2019.utils;

import java.util.Arrays;

public class Log {

    public static boolean DEBUG = false;
    public static boolean INFO = false;

    private static final String WELCOME_MESSAGE = "Avent of code 2019";
    private static final String SEPARATOR       = "==================";
    private static final String BYE_MESSAGE     = "Execution time: %d %s";

    private static void printMessage(String text) {
        printMessage(text, true);
    }
    private static void printMessage(String text, boolean newLine) {
        if(newLine) { System.out.println(text); }
        else { System.out.print(text); }
    }
    private static long startTime;

    public static void welcome() {
        startTime = System.currentTimeMillis();
        printMessage(SEPARATOR);
        printMessage(WELCOME_MESSAGE);
        printMessage(SEPARATOR);
    }

    public static void bye() {
        printMessage(SEPARATOR);
        long time = System.currentTimeMillis()-startTime;
        if(time > 2000) {
            printMessage(String.format(BYE_MESSAGE, time /= 1000, "s"));
        } else {
            printMessage(String.format(BYE_MESSAGE, time, "ms"));
        }

    }

    public static void i(String text) {
        printMessage(text, true);
    }

    public static void i(String text, boolean newLine) {
        printMessage(text, newLine);
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
