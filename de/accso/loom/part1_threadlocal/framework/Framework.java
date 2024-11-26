package de.accso.loom.part1_threadlocal.framework;

import de.accso.loom.part1_threadlocal.context.RegionCode;
import de.accso.loom.part1_threadlocal.context.User;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Framework implements Callback {
    private final ExecutorService executor;
    private final Application app;

    public Framework(Application app, ExecutorService executor) {
        this.app = app;
        this.executor = executor;
    }

    public  static final ThreadLocal<UUID>       correlationIdTL = new ThreadLocal<>();
    private static final ThreadLocal<RegionCode>    regionCodeTL = new ThreadLocal<>();
    private static final ThreadLocal<User>                userTL = new ThreadLocal<>();

    public void serveRequest(RegionCode regionCode, User user, Request request) {
        Runnable task = () -> {
            // set context, per thread
            correlationIdTL.set(UUID.randomUUID());
            regionCodeTL.set(regionCode);
            userTL.set(user);

            app.handle((Callback) this, request);

            // need to remove the context explicitely
            correlationIdTL.remove();
            regionCodeTL.remove();
            userTL.remove();
        };

        executor.submit(task);
    }

    @Override
    public RegionCode getRegion() {
        return regionCodeTL.get();
    }

    @Override
    public UUID getCorrelationId() {
        return correlationIdTL.get();
    }

    @Override
    public User getUser() {
        return userTL.get();
    }
}

