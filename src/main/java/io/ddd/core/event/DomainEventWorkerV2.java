package io.ddd.core.event;

import io.ddd.core.Interaction;
import io.ddd.core.action.Invoke;
import io.ddd.core.action.Persist;
import io.ddd.core.event.invoke.InvokeEvent;
import io.ddd.core.event.persist.PersistEvent;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DomainEventWorkerV2<DATA> implements MethodHandler {
    private final MailBox mailBox;
    private final DATA data;


    public DomainEventWorkerV2(DATA data, Class<? extends Interaction<DATA>> interactionClass) {
        this.data = data;
        this.mailBox = MailBox.get();
    }

    @Override
    public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
        if ("that".equals(method.getName()) && method.getParameterCount() == 0) {
            return data;
        }
        if (method.isAnnotationPresent(Invoke.class)) {
            InvokeEvent invokeEvent = new InvokeEvent(objects, o.getClass(), o, method, 1, null);
            mailBox.post(invokeEvent);
        }
        Object result;
        try {
            if (method.getName().equals("$")) {
                return o;
            }
            result = method1.invoke(o, objects);
        } catch (InvocationTargetException e) {
            // 处理异常，将方法的异常直接抛出处理
            throw e.getTargetException();
        }
        if (method.isAnnotationPresent(Persist.class)) {
            Persist persist = method.getAnnotation(Persist.class);
            PersistEvent persistEvent;
            switch (persist.value()) {
                case INSERT:
                    persistEvent = new PersistEvent(o, o.getClass(), PersistEvent.CRUD.C, new CompletableFuture());
                    break;
                case UPDATE:
                    persistEvent = new PersistEvent(o, o.getClass(), PersistEvent.CRUD.U, new CompletableFuture());
                    break;
                case DELETE:
                    persistEvent = new PersistEvent(o, o.getClass(), PersistEvent.CRUD.D, new CompletableFuture());
                    break;
                default:
                    persistEvent = new PersistEvent(o, o.getClass(), PersistEvent.CRUD.CU, new CompletableFuture());
            }
            mailBox.post(persistEvent);
            try {
                persistEvent.getCompletableFuture().get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        } else if (method.isAnnotationPresent(Invoke.class)) {
            InvokeEvent invokeEvent = new InvokeEvent(objects, o.getClass(), o, method, 2, result);
            mailBox.post(invokeEvent);
        }
        return result;
    }
}
