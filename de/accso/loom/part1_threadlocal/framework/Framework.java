package de.accso.loom.part1_threadlocal.framework;

import de.accso.loom.part1_threadlocal.context.RegionCode;
import de.accso.loom.part1_threadlocal.context.User;
import de.accso.loom.part1_threadlocal.context.CorrelationId;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class Framework implements Callback {
    private final ExecutorService executor;
    private final Application app;

    public Framework(Application app, ExecutorService executor) {
        this.app = app;
        this.executor = executor;
    }

    private static final ThreadLocal<CorrelationId>       correlationIdTL = new ThreadLocal<>();
    private static final ThreadLocal<RegionCode>             regionCodeTL = new ThreadLocal<>();
    private static final ThreadLocal<User>                         userTL = new ThreadLocal<>();

    public void serveRequest(CorrelationId correlationId, RegionCode regionCode, User user, Request request) {
        Objects.requireNonNull(user);

        Runnable task = () -> {
            // set context, per thread
            if (correlationIdTL.get() == null)
                correlationIdTL.set( (correlationId != null) ? correlationId : new CorrelationId(UUID.randomUUID()) );
            if (regionCodeTL.get() == null)
                regionCodeTL.set( (regionCode    != null) ? regionCode    : RegionCode.UNKNOWN );
            if (userTL.get() == null)
                userTL.set( user );

            app.handle((Callback) this, request);

            // need to remove the context explicitly
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
    public CorrelationId getCorrelationId() {
        return correlationIdTL.get();
    }

    @Override
    public User getUser() {
        return userTL.get();
    }
}

