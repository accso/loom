package de.accso.loom.part1_threadlocal;

import de.accso.loom.part1_threadlocal.context.RegionCode;
import de.accso.loom.part1_threadlocal.context.User;
import de.accso.loom.part1_threadlocal.framework.Application;
import de.accso.loom.part1_threadlocal.framework.Framework;
import de.accso.loom.part1_threadlocal.framework.Request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static de.accso.loom.part1_threadlocal.context.RegionCode.NA;

public class ThreadLocalExample {
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Framework framework = setupFramework();

        sendRequest(framework);
        executor.shutdown();
    }

    private static Framework setupFramework() {
        Application application = new MyApp();
        Framework framework = new Framework(application, executor);
        return framework;
    }

    private static void sendRequest(Framework framework) {
        // these scoped values could come from a config setting or from a user login, respectively
        RegionCode regionCode = NA;
        User user = new User("jane123", true);

        Request request = new Request("Jane", "Doe", "New York");
        framework.serveRequest(regionCode, user, request);
    }
}
