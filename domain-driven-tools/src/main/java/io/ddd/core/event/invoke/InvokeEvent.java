package io.ddd.core.event.invoke;


import io.ddd.core.event.AbstractEvent;

import java.lang.reflect.Method;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/29.
 */
public class InvokeEvent<T> extends AbstractEvent {
    private final Object[] parameters;
    private final Class<T> targetType;
    private final T target;
    private final Method method;
    private final int position;
    private final Object returnObject;

    public InvokeEvent(Object[] parameters, Class<T> targetType, T target, Method method, int position, Object returnObject) {
        this.parameters = parameters;
        this.targetType = targetType;
        this.target = target;
        this.method = method;
        this.position = position;
        this.returnObject = returnObject;
    }


    public Class<T> getTargetType() {
        return targetType;
    }

    public T getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public int getPosition() {
        return position;
    }


    public Object[] getParameters() {
        return parameters;
    }

    public Object getReturnObject() {
        return returnObject;
    }

}
