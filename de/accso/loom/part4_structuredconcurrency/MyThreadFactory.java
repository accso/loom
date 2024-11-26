package de.accso.loom.part4_structuredconcurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setName("task_" + thread.threadId());
        return thread;
    }
}
