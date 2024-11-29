package de.accso.loom.part1_future;

import de.accso.loom.part1_future.music.BigBand;

import java.util.concurrent.Executors;

import static de.accso.loom.util.LogHelper.logError;

public class FutureExample {

    public static void main(String[] args) {
        try (var executor = Executors.newFixedThreadPool(2)) {

            BigBand bigBand = new BigBandBuilderUsingFutures(executor).getAllInstrumentsAndMusicians();

            if (bigBand == null) {
                logError("Too bad ... No BigBand could be built.");
            } else {
                bigBand.startToPlay();
            }
        }
    }

}
