package io.ddd.core.event;

import io.ddd.core.Role;
import io.ddd.core.action.Invoke;
import io.ddd.core.action.Persist;
import io.ddd.core.event.invoke.InvokeEvent;
import io.ddd.core.event.persist.PersistEvent;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DomainEventWorker implements MethodHandler {
    private final MailBox mailBox;

    public DomainEventWorker() {
        this.mailBox = MailBox.get();
    }

    @Override
    public Object invoke(Object o, Method method, Method method1, Object[] objects) throws Throwable {
        if (o instanceof Role) {
            if (method.isAnnotationPresent(Invoke.class)) {
                InvokeEvent invokeEvent = new InvokeEvent(objects, o.getClass(), o, method, 1, null);
                mailBox.post(invokeEvent);
            }
        }
        Object result;
        try {
            if (method.getName().equals("$")) {
                return o;
            }
            result = method1.invoke(o, objects);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        if (o instanceof Role) {
            if (method.isAnnotationPresent(Persist.class)) {
                Persist persist = method.getAnnotation(Persist.class);
                PersistEvent persistEvent;
                switch (persist.value()) {
                    case INSERT:
                        persistEvent = new PersistEvent(((Role) o).that(), ((Role) o).that().getClass(), PersistEvent.CRUD.C, new CompletableFuture());
                        break;
                    case UPDATE:
                        persistEvent = new PersistEvent(((Role) o).that(), ((Role) o).that().getClass(), PersistEvent.CRUD.U, new CompletableFuture());
                        break;
                    case DELETE:
                        persistEvent = new PersistEvent(((Role) o).that(), ((Role) o).that().getClass(), PersistEvent.CRUD.D, new CompletableFuture());
                        break;
                    default:
                        persistEvent = new PersistEvent(((Role) o).that(), ((Role) o).that().getClass(), PersistEvent.CRUD.CU, new CompletableFuture());
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
        }
        return result;
    }
}
