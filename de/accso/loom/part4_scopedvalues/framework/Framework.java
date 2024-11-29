package de.accso.loom.part4_scopedvalues.framework;

import de.accso.loom.part4_scopedvalues.context.CorrelationId;
import de.accso.loom.part4_scopedvalues.context.RegionCode;
import de.accso.loom.part4_scopedvalues.context.User;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class Framework {
    private final ExecutorService executor;
    private final Application app;

    public Framework(Application app, ExecutorService executor) {
        this.app = app;
        this.executor = executor;
    }

    public static final ScopedValue<CorrelationId>       correlationIdCtx = ScopedValue.newInstance();
    public static final ScopedValue<RegionCode>             regionCodeCtx = ScopedValue.newInstance();
    public static final ScopedValue<User>                         userCtx = ScopedValue.newInstance();

    public void serveRequest(CorrelationId correlationId, RegionCode regionCode, User user, Request request) {
        Objects.requireNonNull(user);

        Runnable task = () -> {
            ScopedValue
                   .where(correlationIdCtx, (correlationId != null) ? correlationId : new CorrelationId(UUID.randomUUID()) )
                   .where(regionCodeCtx,    (regionCode    != null) ? regionCode    : RegionCode.UNKNOWN)
                   .where(userCtx,          user)
                    .run(() -> {
                        app.handle(request);
                    });

            // no need to remove context explicitly, context is no longer bound!
            assert( ! correlationIdCtx.isBound() );
            assert( !    regionCodeCtx.isBound() );
            assert( !          userCtx.isBound() );
        };

        executor.submit(task);
    }
}

