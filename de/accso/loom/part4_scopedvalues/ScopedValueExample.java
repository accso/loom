package de.accso.loom.part4_scopedvalues;

import de.accso.loom.part4_scopedvalues.context.RegionCode;
import de.accso.loom.part4_scopedvalues.context.User;
import de.accso.loom.part4_scopedvalues.framework.Framework;
import de.accso.loom.part4_scopedvalues.framework.Request;

import java.util.concurrent.Executors;

import static de.accso.loom.part4_scopedvalues.context.RegionCode.EU;

public class ScopedValueExample {

    public static void main(String[] args) {
        try (var executor = Executors.newFixedThreadPool(2)) {
            Framework framework = new Framework(new MyApp(), executor);

            sendRequest(framework);
            executor.shutdown();
        }
    }

    private static void sendRequest(Framework framework) {
        // these values could come from a config setting or from a user login, respectively
        RegionCode regionCode = EU;
        User user = new User("john123", true);

        Request request = new Request("John", "Doe", "Frankfurt");
        framework.serveRequest(null, regionCode, user, request);
    }
}
