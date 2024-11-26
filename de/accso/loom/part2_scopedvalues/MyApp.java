package de.accso.loom.part2_scopedvalues;

import de.accso.loom.part2_scopedvalues.context.RegionCode;
import de.accso.loom.part2_scopedvalues.context.User;
import de.accso.loom.part2_scopedvalues.framework.Application;
import de.accso.loom.part2_scopedvalues.framework.Callback;
import de.accso.loom.part2_scopedvalues.framework.Request;

import java.util.UUID;

import static de.accso.loom.util.LogHelper.logError;

public class MyApp implements Application {

    @Override
    public void handle(Callback callback, Request request) {
        User user = callback.getUser();

        if (user == null || !user.loggedIn()) {
            throw new RuntimeException("user not logged in");
        }
        else {
            // get context information
            UUID correlationId = callback.getCorrelationId();
            RegionCode region  = callback.getRegion();

            // log request
            String textToLog = String.format("[%s] [%s] - Handling scopedvalue based request for '%s %s', '%s'",
                    correlationId,
                    region,
                    request.firstName(), request.lastName(), request.address());
            logError(textToLog);

            // ... more business code goes here
        }
    }
}
