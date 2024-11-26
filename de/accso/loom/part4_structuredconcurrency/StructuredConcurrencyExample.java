package de.accso.loom.part4_structuredconcurrency;

import de.accso.loom.part4_structuredconcurrency.music.BigBand;

import static de.accso.loom.util.LogHelper.logError;

public class StructuredConcurrencyExample {

    public static void main(String[] args) {
        BigBand bigBand = new BigBandBuilderUsingStructuredConcurrency().getAllInstrumentsAndMusicians();

        if (bigBand == null) {
            logError("Too bad ... No BigBand could be built.");
        }
        else {
            bigBand.startToPlay();
        }
    }

}
