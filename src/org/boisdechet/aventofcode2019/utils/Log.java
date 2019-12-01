package org.boisdechet.aventofcode2019.utils;

public class Log {

    public static final String WELCOME_MESSAGE = "Avent of code 2019";
    public static final String SEPARATOR       = "==================";

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

    public static void w(String text) {
        printMessage(String.format("[W] %s", text));
    }
}
