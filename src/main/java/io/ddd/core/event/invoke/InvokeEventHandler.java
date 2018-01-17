package io.ddd.core.event.invoke;


import io.ddd.core.event.AbstractEvent;
import io.ddd.core.event.EventHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2018/1/3.
 */
public interface InvokeEventHandler extends EventHandler {

    void onInvoke(InvokeEvent persistEvent) throws InvocationTargetException, IllegalAccessException;

    @Override
    default void onEvent(AbstractEvent event) {
        if (event instanceof InvokeEvent == false) {
            return;
        }
        if (!canHandle(InvokeEvent.class.cast(event))) {
            return;
        }
        try {
            onInvoke(InvokeEvent.class.cast(event));
        } catch (InvocationTargetException e) {
            event.getCompletableFuture().completeExceptionally(e.getTargetException());
        } catch (IllegalAccessException e) {
            event.getCompletableFuture().completeExceptionally(e);
        }
    }

    boolean canHandle(InvokeEvent event);
}
