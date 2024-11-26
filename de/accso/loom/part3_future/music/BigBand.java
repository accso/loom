package de.accso.loom.part3_future.music;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.accso.loom.util.LogHelper.log;

public record BigBand(List<Instrument> instruments, List<Musician> musicians) {
    public BigBand {
        assert instruments.size() == musicians.size();
    }

    public void startToPlay() {
        // mix them all up
        Collections.shuffle(instruments);
        Collections.shuffle(musicians);

        String bigBandSetup =
                IntStream.range(0, instruments.size())
                .mapToObj(i ->  String.format(" %s plays '%s'", musicians.get(i), instruments.get(i)))
                .collect(Collectors.joining(", "));

        log("And finally ... Today's big band setup is: " + bigBandSetup);
    }
}
