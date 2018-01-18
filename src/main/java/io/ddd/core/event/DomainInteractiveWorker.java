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

public class DomainInteractiveWorker<DATA> implements MethodHandler {
    private final MailBox mailBox;
    private final DATA data;
    private final Class<? extends Interaction<DATA>> interactionClass;


    public DomainInteractiveWorker(DATA data, Class<? extends Interaction<DATA>> interactionClass) {
        this.data = data;
        this.interactionClass = interactionClass;
        this.mailBox = MailBox.get();
    }

    @Override
    public Object invoke(Object invokerObject, Method originalMethod, Method proxyMethod, Object[] args) throws Throwable {
        if ("that".equals(originalMethod.getName()) && originalMethod.getParameterCount() == 0) {
            return data;
        }
        if (originalMethod.isAnnotationPresent(Invoke.class)) {
            InvokeEvent invokeEvent = new InvokeEvent(args, invokerObject.getClass(), invokerObject, originalMethod, 1, null);
            mailBox.post(invokeEvent);
        }
        Object result;
        try {
            if (originalMethod.getName().equals("$")) {
                return invokerObject;
            }
            result = proxyMethod.invoke(invokerObject, args);
        } catch (InvocationTargetException e) {
            // 处理异常，将方法的异常直接抛出处理
            throw e.getTargetException();
        }
        if (originalMethod.isAnnotationPresent(Persist.class)) {
            Persist persist = originalMethod.getAnnotation(Persist.class);
            PersistEvent persistEvent;
            switch (persist.value()) {
                case INSERT:
                    persistEvent = new PersistEvent(invokerObject, invokerObject.getClass(), PersistEvent.CRUD.C, new CompletableFuture());
                    break;
                case UPDATE:
                    persistEvent = new PersistEvent(invokerObject, invokerObject.getClass(), PersistEvent.CRUD.U, new CompletableFuture());
                    break;
                case DELETE:
                    persistEvent = new PersistEvent(invokerObject, invokerObject.getClass(), PersistEvent.CRUD.D, new CompletableFuture());
                    break;
                default:
                    persistEvent = new PersistEvent(invokerObject, invokerObject.getClass(), PersistEvent.CRUD.CU, new CompletableFuture());
            }
            mailBox.post(persistEvent);
            try {
                persistEvent.getCompletableFuture().get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        } else if (originalMethod.isAnnotationPresent(Invoke.class)) {
            InvokeEvent invokeEvent = new InvokeEvent(args, invokerObject.getClass(), invokerObject, originalMethod, 2, result);
            mailBox.post(invokeEvent);
        }
        return result;
    }

    public Class<? extends Interaction<DATA>> getInteractionClass() {
        return interactionClass;
    }
}
