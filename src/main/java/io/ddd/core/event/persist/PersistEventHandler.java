package io.ddd.core.event.persist;


import io.ddd.core.event.AbstractEvent;
import io.ddd.core.event.EventHandler;

import java.lang.reflect.InvocationTargetException;

public interface PersistEventHandler extends EventHandler {

    void onPersist(PersistEvent persistEvent) throws InvocationTargetException, IllegalAccessException;

    @Override
    default void onEvent(AbstractEvent event) {
        if (event instanceof PersistEvent == false) {
            return;
        }
        if (!canHandle(PersistEvent.class.cast(event))) {
            return;
        }
        try {
            onPersist(PersistEvent.class.cast(event));
        } catch (InvocationTargetException e) {
            event.getCompletableFuture().completeExceptionally(e.getTargetException());
        } catch (IllegalAccessException e) {
            event.getCompletableFuture().completeExceptionally(e);
        }
    }

    boolean canHandle(PersistEvent event);
}
