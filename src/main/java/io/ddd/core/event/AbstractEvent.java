package io.ddd.core.event;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractEvent {
    private final CompletableFuture completableFuture = new CompletableFuture();
    private final String id;

    protected AbstractEvent() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public CompletableFuture getCompletableFuture() {
        return completableFuture;
    }
}
