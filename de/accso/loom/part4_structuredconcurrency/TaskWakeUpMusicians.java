package de.accso.loom.part4_structuredconcurrency;

import de.accso.loom.part4_structuredconcurrency.music.Musician;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.accso.loom.util.LogHelper.logWithTime;
import static de.accso.loom.util.PauseHelper.randomPause;

public final class TaskWakeUpMusicians implements Callable<List<Musician>> {
    private final String taskName = TaskWakeUpMusicians.class.getSimpleName();

    @Override
    public List<Musician> call() {
        logWithTime(taskName + " - Waking up all musicians ... starting");

        // now let's enforce an error here at musician number 3
// !!! A Throw an error (which not only stops this task but _all_ of the tasks)
//        AtomicInteger countDownToError = new AtomicInteger(3);

        List<Musician> musicians = Arrays.stream(Musician.values())
                .peek(_ -> randomPause(100, 1_000)) //  it takes a while to wake up each musician
// !!! A Throw an error (which not only stops this task but _all_ of the tasks)
//                .peek(_ -> {
//                    countDownToError.decrementAndGet();
//                    if (countDownToError.get() == 0) {
//                        String errorText = "Boom! Error while working on waking up all musicians!";
//                        logError(errorText);
//                        throw new RuntimeException(errorText);
//                    }
//                })
                .peek(musician -> logWithTime(taskName +" - Musician " + musician.name() + " woke up ..."))
                .collect(Collectors.toList());

        logWithTime(taskName + " - Waking up all musicians ... done");

        return musicians;
    }
}
