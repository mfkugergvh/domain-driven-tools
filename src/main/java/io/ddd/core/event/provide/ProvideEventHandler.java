package io.ddd.core.event.provide;


import io.ddd.core.event.AbstractEvent;
import io.ddd.core.event.EventHandler;

import java.lang.reflect.InvocationTargetException;

public class ProvideEventHandler implements EventHandler {
    private final Provider provider;

    public ProvideEventHandler(Provider provider) {
        this.provider = provider;
    }

    public void onProvide(ProvideEvent provideEvent) throws InvocationTargetException, IllegalAccessException {
        Object value = provider.provide();
        provideEvent.getProvideValueFuture().complete(value);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof ProvideEvent == false) {
            return;
        }
        if (!canHandle(ProvideEvent.class.cast(event))) {
            return;
        }
        try {
            onProvide(ProvideEvent.class.cast(event));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e.getCause());
        }
    }

    public boolean canHandle(ProvideEvent event) {
        if (!event.getProvideValueFuture().isDone()) {
            if (event.getProvideValueType().isAssignableFrom(provider.getProvidedType())) {
                return true;
            }
        }
        return false;
    }
}
