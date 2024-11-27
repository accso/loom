package de.accso.loom.part2_scopedvalues.framework;

import de.accso.loom.part2_scopedvalues.context.RegionCode;
import de.accso.loom.part2_scopedvalues.context.User;

import java.util.Objects;
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

    private static final ScopedValue<UUID>       correlationIdCtx = ScopedValue.newInstance();
    private static final ScopedValue<RegionCode>    regionCodeCtx = ScopedValue.newInstance();
    private static final ScopedValue<User>                userCtx = ScopedValue.newInstance();

    public void serveRequest(UUID correlationId, RegionCode regionCode, User user, Request request) {
        Objects.requireNonNull(user);

        Runnable task = () -> {
            ScopedValue
                    // set context
                       .where(correlationIdCtx, (correlationId != null) ? correlationId : UUID.randomUUID())
                       .where(regionCodeCtx,    (regionCode    != null) ? regionCode    : RegionCode.UNKNOWN)
                       .where(userCtx,          user)
                    // run
                    .run(() -> {
                        app.handle((Callback) this, request);
                    });

            // no need to remove context explicitely, context is no longer bound!
            assert( ! correlationIdCtx.isBound() );
            assert( !    regionCodeCtx.isBound() );
            assert( !          userCtx.isBound() );
        };

        executor.submit(task);
    }

    @Override
    public RegionCode getRegion() {
        return regionCodeCtx.get();
    }

    @Override
    public UUID getCorrelationId() {
        return correlationIdCtx.get();
    }

    @Override
    public User getUser() {
        return userCtx.get();
    }
}

