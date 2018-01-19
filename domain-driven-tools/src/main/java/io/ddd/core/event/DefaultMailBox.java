package io.ddd.core.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.ddd.core.event.invoke.AnnotationInvokeEventHandler;
import io.ddd.core.event.invoke.annotation.OnInvoke;
import io.ddd.core.event.persist.AnnotationPersistEventHandler;
import io.ddd.core.event.persist.annotation.OnPersist;
import io.ddd.core.event.provide.ProvideEventHandler;
import io.ddd.core.event.provide.Provider;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArraySet;

public class DefaultMailBox implements MailBox {

    private final EventBus eventBus;
    private final CopyOnWriteArraySet<EventHandler> eventHandlers = new CopyOnWriteArraySet<>();

    public DefaultMailBox(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }


    public DefaultMailBox() {
        this.eventBus = new EventBus();
        this.eventBus.register(this);
    }

    @Override
    public void post(Object mail) {
        eventBus.post(mail);
    }

    @Override
    public void registerReceiver(Object receiver) {
        if (receiver == null) return;
        Class<?> receiverClass = receiver.getClass();
        if (receiver instanceof Provider) {
            eventHandlers.add(new ProvideEventHandler((Provider) receiver));
        }
        while (receiverClass != Object.class) {
            Method[] methods = receiverClass.getDeclaredMethods();

            if (methods == null) {
                continue;
            }
            for (Method method : methods) {
                if (method.getParameterCount() == 1 && method.isAnnotationPresent(OnPersist.class)) {
                    eventHandlers.add(new AnnotationPersistEventHandler(receiver, method, method.getAnnotation(OnPersist.class)));
                } else if (method.isAnnotationPresent(OnInvoke.class)) {
                    eventHandlers.add(new AnnotationInvokeEventHandler(receiver, method, method.getAnnotation(OnInvoke.class)));
                }
            }
            receiverClass = receiverClass.getSuperclass();
        }
    }

    @SuppressWarnings("unchecked")
    @Subscribe
    @AllowConcurrentEvents
    private void dispatch(AbstractEvent event) {
        try {
            eventHandlers.forEach(eventHandler -> {
                if (event.getCompletableFuture().isCompletedExceptionally()) {
                    return;
                }
                eventHandler.onEvent(event);
            });
            if (!event.getCompletableFuture().isCompletedExceptionally()) {
                event.getCompletableFuture().complete(event.getId());
            }
        } catch (Throwable e) {
            event.getCompletableFuture().completeExceptionally(e);
        }
    }

    @Override
    public void unRegisterReceiver(Object receiver) {
        if (receiver == null) return;
        Class<?> receiverClass = receiver.getClass();
        while (receiverClass != Object.class) {
            Method[] methods = receiverClass.getDeclaredMethods();

            if (methods == null) {
                continue;
            }
            for (Method method : methods) {
                if (method.getParameterCount() == 1 && method.isAnnotationPresent(OnPersist.class)) {
                    eventHandlers.remove(new AnnotationPersistEventHandler(receiver, method, method.getAnnotation(OnPersist.class)));
                } else if (method.isAnnotationPresent(OnInvoke.class)) {
                    eventHandlers.remove(new AnnotationInvokeEventHandler(receiver, method, method.getAnnotation(OnInvoke.class)));
                }
            }
            receiverClass = receiverClass.getSuperclass();
        }
    }
}
