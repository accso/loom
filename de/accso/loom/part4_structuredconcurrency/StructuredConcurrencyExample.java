package de.accso.loom.part4_structuredconcurrency;

import de.accso.loom.part4_structuredconcurrency.music.BigBand;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static de.accso.loom.util.LogHelper.logError;

public class StructuredConcurrencyExample {

    public static void main(String[] args) {
        MyThreadFactory myThreadFactory = new MyThreadFactory();

        BigBand bigBand = new BigBandBuilderUsingStructuredConcurrency(myThreadFactory).getAllInstrumentsAndMusicians();

        if (bigBand == null) {
            logError("Too bad ... No BigBand could be built.");
        } else {
            bigBand.startToPlay();
        }
    }

}
