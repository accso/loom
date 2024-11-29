package de.accso.loom.part3_threadlocal;

import de.accso.loom.part3_threadlocal.context.RegionCode;
import de.accso.loom.part3_threadlocal.context.User;
import de.accso.loom.part3_threadlocal.framework.Application;
import de.accso.loom.part3_threadlocal.framework.Callback;
import de.accso.loom.part3_threadlocal.framework.Request;

import java.util.UUID;

import static de.accso.loom.util.LogHelper.log;

public class MyApp implements Application {

    @Override
    public void handle(Callback callback, Request request) {
        User user = callback.getUser();

        if (user == null || !user.loggedIn()) {
            throw new RuntimeException("user not logged in");
        }
        else {
            // get context information
            UUID correlationId = callback.getCorrelationId().id();
            RegionCode region  = callback.getRegion();

            // log request
            String textToLog = String.format("[%s] [%s] - Handling threadlocal based request for '%s %s', '%s'",
                    correlationId,
                    region,
                    request.firstName(), request.lastName(), request.address());

            log(textToLog);

            // Could change theoretically the mutable thread-local value here:
            // Framework.regionCodeTL.set(RegionCode.OC);

            // ... more business code goes here
        }
    }
}
