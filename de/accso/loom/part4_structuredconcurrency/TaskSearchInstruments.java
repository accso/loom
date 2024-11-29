package de.accso.loom.part4_structuredconcurrency;

import de.accso.loom.part4_structuredconcurrency.music.Instrument;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.accso.loom.util.LogHelper.logWithTime;
import static de.accso.loom.util.PauseHelper.randomPause;

public final class TaskSearchInstruments implements Callable<List<Instrument>> {
    private final String taskName = TaskSearchInstruments.class.getSimpleName();

    @Override
    public List<Instrument> call() // !!! B throws InstrumentNotFoundException
    {
        logWithTime(taskName + " - Searching all instruments ... starting");

// !!! B handling of checked exception
//        if (new Random().nextBoolean() == true) {
//            throw new InstrumentNotFoundException();
//        }

        List<Instrument> instruments = Arrays.stream(Instrument.values())
                .peek(_ -> randomPause(50, 500)) // it takes a while to find each instrument
                .peek(instrument -> logWithTime(taskName + " - Instrument " + instrument.name() + " found and ready ..."))
                .collect(Collectors.toList());

        logWithTime(taskName + " - Searching all instruments ... done");

        return instruments;
    }

    public static class InstrumentNotFoundException extends Exception {}
}
