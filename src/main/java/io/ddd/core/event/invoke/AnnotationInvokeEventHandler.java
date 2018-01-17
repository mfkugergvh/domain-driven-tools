package io.ddd.core.event.invoke;


import io.ddd.core.event.invoke.annotation.OnInvoke;
import org.springframework.util.AntPathMatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationInvokeEventHandler implements InvokeEventHandler {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final Object instance;
    private final Method cachedMethod;
    private final OnInvoke invoke;

    public AnnotationInvokeEventHandler(Object instance, Method method, OnInvoke invoke) {
        this.instance = instance;
        this.cachedMethod = method;
        this.invoke = invoke;
    }

    @Override
    public void onInvoke(InvokeEvent invokeEvent) throws InvocationTargetException, IllegalAccessException {
        if(!this.cachedMethod.isAccessible()){
            this.cachedMethod.setAccessible(true);
        }
        if (invoke.position() == 1) {
            if (this.cachedMethod.getParameterCount() == 0) {
                this.cachedMethod.invoke(instance);
            } else {
                Object[] parameters = Arrays.copyOf(invokeEvent.getParameters(), cachedMethod.getParameterCount());
                this.cachedMethod.invoke(instance, parameters);
            }
        } else if (invoke.position() == 2) {
            this.cachedMethod.invoke(instance, invokeEvent.getReturnObject());
        }
    }

    @Override
    public boolean canHandle(InvokeEvent event) {
        if (invoke.allowGeneric()) {
            if (!invoke.value().isAssignableFrom(event.getTargetType())) {
                return false;
            }
        } else {
            if (invoke.value() != event.getTargetType()) {
                return false;
            }
        }
        if (!antPathMatcher.match(invoke.methodName(), event.getMethod().getName())) {
            return false;
        }
        if (invoke.position() == 1) {
            if (this.cachedMethod.getParameterCount() == 0) return true;
            Class[] types = this.cachedMethod.getParameterTypes();
            Class[] tTypes = event.getMethod().getParameterTypes();
            for (int i = 0; i < types.length; i++) {
                if (!types[i].isAssignableFrom(tTypes[i])) {
                    return false;
                }
            }
            return true;
        } else if (invoke.position() == 2) {
            if (this.cachedMethod.getParameterCount() != 1) return false;
            return this.cachedMethod.getParameterTypes()[0].isAssignableFrom(event.getMethod().getReturnType());
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnotationInvokeEventHandler that = (AnnotationInvokeEventHandler) o;

        if (!instance.equals(that.instance)) return false;
        if (!cachedMethod.equals(that.cachedMethod)) return false;
        return invoke.equals(that.invoke);
    }

    @Override
    public int hashCode() {
        int result = instance.hashCode();
        result = 31 * result + cachedMethod.hashCode();
        result = 31 * result + invoke.hashCode();
        return result;
    }
}
