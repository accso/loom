package de.accso.loom.part4_structuredconcurrency;

import de.accso.loom.part4_structuredconcurrency.music.BigBand;
import de.accso.loom.part4_structuredconcurrency.music.Instrument;
import de.accso.loom.part4_structuredconcurrency.music.Musician;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static de.accso.loom.util.LogHelper.logWithTime;
import static de.accso.loom.util.PauseHelper.randomPause;
import static java.util.concurrent.StructuredTaskScope.Subtask.State.FAILED;

public class BigBandBuilderUsingStructuredConcurrency {

    private final ThreadFactory threadFactory;

    public BigBandBuilderUsingStructuredConcurrency(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public BigBand getAllInstrumentsAndMusicians() {
        int maxTimeOutInMs = 20_000; // 1s max for each musician => 17s max

        // or StructuredTaskScope.ShutdownOnFailure() or StructuredTaskScope.ShutdownOnSuccess()
        try (var scope = new StructuredTaskScope<>.ShutdownOnFailure("bigBandScope", threadFactory)) {

            // (1) create tasks and fork them
            logWithTime("Now forking to wake up all musicians");
            StructuredTaskScope.Subtask<List<Musician>>     musiciansTask = scope.fork( new TaskWakeUpMusicians() );

            logWithTime("Now forking to search all instruments");
            StructuredTaskScope.Subtask<List<Instrument>> instrumentsTask = scope.fork( new TaskSearchInstruments() );

            // (2) wait until all tasks are executed in parallel
            logWithTime("Now joining both tasks ... waiting ...");
            scope.joinUntil( Instant.now().plusMillis(maxTimeOutInMs) );
            logWithTime("Both tasks joined, all done");

            // (3) error handling
            scope.throwIfFailed();
//            if (musiciansTask.state() == FAILED || instrumentsTask.state() == FAILED) {
//                return null;
//            }

            // (4) get all results and bring instruments and musicians together 🎵
            List<Musician>     musicians =   musiciansTask.get();
            List<Instrument> instruments = instrumentsTask.get();
            return new BigBand(instruments, musicians);
        }
        catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new RuntimeException(ex);
        }
    }
}
