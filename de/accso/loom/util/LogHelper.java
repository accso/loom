package de.accso.loom.util;

import java.time.Clock;

public final class LogHelper {

    public static void logWithTime(String text) {
        System.out.printf("[%d] [%s] %s%n",
                Clock.systemUTC().millis(),
                Thread.currentThread().getName(),
                text);
    }

    public static void log(String text) {
        System.out.printf("[%s] %s%n",
                Thread.currentThread().getName(),
                text);
    }

    public static void logError(String text) {
        System.err.printf("[%s] %s%n",
                Thread.currentThread().getName(),
                text);
    }

}

