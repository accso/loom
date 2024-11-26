package de.accso.loom.util;

import java.time.Duration;
import java.util.Random;

public final class PauseHelper {
    private static final Random RANDOM = new Random();

    public static void randomPause(int waitMinInMs, int waitMaxInMs) {
        Duration pauseInMs = Duration.ofMillis( RANDOM.nextLong(waitMinInMs, waitMaxInMs) );

        try {
            Thread.sleep( pauseInMs );
        }
        catch (InterruptedException irex) {
            throw new RuntimeException( irex );
        }
    }
}
