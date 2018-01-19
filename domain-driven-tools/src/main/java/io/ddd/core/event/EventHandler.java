package io.ddd.core.event;

public interface EventHandler {
    void onEvent(AbstractEvent event);
}
