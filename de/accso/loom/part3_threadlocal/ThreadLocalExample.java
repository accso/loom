package de.accso.loom.part3_threadlocal;

import de.accso.loom.part3_threadlocal.context.RegionCode;
import de.accso.loom.part3_threadlocal.context.User;
import de.accso.loom.part3_threadlocal.framework.Framework;
import de.accso.loom.part3_threadlocal.framework.Request;

import java.util.concurrent.Executors;

import static de.accso.loom.part3_threadlocal.context.RegionCode.NA;

public class ThreadLocalExample {

    public static void main(String[] args) {
        try (var executor = Executors.newFixedThreadPool(2)) {
            Framework framework = new Framework(new MyApp(), executor);

            sendRequest(framework);
            executor.shutdown();
        }
    }

    private static void sendRequest(Framework framework) {
        // these values could come from a config setting or from a user login, respectively
        RegionCode regionCode = NA;
        User user = new User("jane123", true);

        Request request = new Request("Jane", "Doe", "New York");
        framework.serveRequest(null, regionCode, user, request);
    }
}
