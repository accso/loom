package de.accso.loom.part2_structuredconcurrency;

import de.accso.loom.part2_structuredconcurrency.music.BigBand;
import de.accso.loom.part2_structuredconcurrency.music.Instrument;
import de.accso.loom.part2_structuredconcurrency.music.Musician;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;

import static de.accso.loom.util.LogHelper.logError;
import static de.accso.loom.util.LogHelper.logWithTime;

public class BigBandBuilderUsingStructuredConcurrency {

    private final ThreadFactory threadFactory;

    public BigBandBuilderUsingStructuredConcurrency(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public BigBand getAllInstrumentsAndMusicians() {
        int maxTimeOutInMs = 20_000; // 1s max for each musician => 17s max

        // or StructuredTaskScope<>() or StructuredTaskScope.ShutdownOnSuccess()
        try (var scope = new StructuredTaskScope.ShutdownOnFailure("scope_big-band", threadFactory)) {

            // (1) create tasks and fork them
            logWithTime("Now forking to wake up all musicians");
            StructuredTaskScope.Subtask<List<Musician>>     musiciansTask = scope.fork(new TaskWakeUpMusicians());

            logWithTime("Now forking to search all instruments");
            StructuredTaskScope.Subtask<List<Instrument>> instrumentsTask = scope.fork(new TaskSearchInstruments());

            // (2) wait until all tasks are executed in parallel
            logWithTime("Now joining both tasks ... waiting ...");
            scope.joinUntil( Instant.now().plusMillis(maxTimeOutInMs) );
            logWithTime("Both tasks joined, all done");

            // (3) error handling
//            if (musiciansTask.state() == FAILED) logError(  musiciansTask.exception());
//            if (musiciansTask.state() == FAILED) logError(instrumentsTask.exception());
            scope.throwIfFailed();

            // (4) get all results and bring instruments and musicians together 🎵
            List<Musician>     musicians = musiciansTask.get();
            List<Instrument> instruments = instrumentsTask.get();
            return new BigBand(instruments, musicians);

        }
        catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new RuntimeException(ex);  // !!! B throw ex.getCause();
        }
    }
}
