package io.ddd.core.tools;

import io.ddd.core.Interaction;
import io.ddd.core.delegate.Delegate;
import io.ddd.core.event.DomainInteractiveWorker;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class $ {
    public static <DATA, T extends Interaction<DATA>> T cast(Class<T> interactive, DATA data) {
        try {
            if (!interactive.isInterface()) {
                throw new IllegalArgumentException("Class<T> interactive should be A Interface, Actually Class is [" + interactive.getName() + "]");
            }
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setInterfaces(new Class[]{interactive});
            T role = interactive.cast(proxyFactory.create(new Class[0], new Object[0], new DomainInteractiveWorker(data, interactive)));
            return role;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static <P> Delegate.$Consumer<P> consumer() {
        return new Delegate.$Consumer<>();
    }

    public static <P, R> Delegate.$Function<P, R> function() {
        return new Delegate.$Function<>();
    }

    public static Delegate.$Runnable runnable() {
        return new Delegate.$Runnable();
    }

    public static <R> Delegate.$Supplier<R> supplier() {
        return new Delegate.$Supplier<>();
    }
}
