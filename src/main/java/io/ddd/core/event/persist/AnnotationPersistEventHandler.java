package io.ddd.core.event.persist;

import io.ddd.core.action.Mode;
import io.ddd.core.event.persist.annotation.OnPersist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationPersistEventHandler implements PersistEventHandler {
    private final Object instance;
    private final Method cachedMethod;
    private final OnPersist onPersist;

    public AnnotationPersistEventHandler(Object instance, Method method, OnPersist onPersist) {
        this.instance = instance;
        this.cachedMethod = method;
        this.onPersist = onPersist;
    }

    @Override
    public void onPersist(PersistEvent persistEvent) throws InvocationTargetException, IllegalAccessException {
        cachedMethod.invoke(instance, persistEvent.getData());
    }

    @Override
    public boolean canHandle(PersistEvent event) {
        if (this.cachedMethod.getParameterCount() != 1) return false;
        if (onPersist.allowGenericType()) {
            return modeMatch(event) && cachedMethod.getParameterTypes()[0].isAssignableFrom(event.getType());
        } else {
            return modeMatch(event) && cachedMethod.getParameterTypes()[0] == event.getType();
        }
    }

    public boolean modeMatch(PersistEvent event) {
        return onPersist.mode() == Mode.ANY
                || (event.getCrud() == PersistEvent.CRUD.C && onPersist.mode() == Mode.INSERT)
                || (event.getCrud() == PersistEvent.CRUD.CU && onPersist.mode() == Mode.UPSERT)
                || (event.getCrud() == PersistEvent.CRUD.D && onPersist.mode() == Mode.DELETE)
                || (event.getCrud() == PersistEvent.CRUD.U && onPersist.mode() == Mode.UPDATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnotationPersistEventHandler that = (AnnotationPersistEventHandler) o;

        if (!instance.equals(that.instance)) return false;
        if (!cachedMethod.equals(that.cachedMethod)) return false;
        return onPersist.equals(that.onPersist);
    }

    @Override
    public int hashCode() {
        int result = instance.hashCode();
        result = 31 * result + cachedMethod.hashCode();
        result = 31 * result + onPersist.hashCode();
        return result;
    }
}
