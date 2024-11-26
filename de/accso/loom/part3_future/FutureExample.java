package de.accso.loom.part3_future;

import de.accso.loom.part3_future.music.BigBand;

import static de.accso.loom.util.LogHelper.logError;

public class FutureExample {

    public static void main(String[] args) {
        BigBand bigBand = new BigBandBuilderUsingFutures().getAllInstrumentsAndMusicians();

        if (bigBand == null) {
            logError("Too bad ... No BigBand could be built.");
        }
        else {
            bigBand.startToPlay();
        }
    }

}
