package de.accso.loom.part4_structuredconcurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        // normal or virtual thread?
        // Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        Thread thread = Thread.ofVirtual().factory().newThread(runnable);

        String name = String.format("%s-%s-%d",
                thread.isVirtual() ? "VT" : "T", thread.getName(), thread.threadId());
        thread.setName(name);
        return thread;
    }
}
