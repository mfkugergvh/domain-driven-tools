package io.ddd.core.tools;

import io.ddd.core.Interaction;
import io.ddd.core.delegate.Delegate;
import io.ddd.core.event.DomainEventWorkerV2;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class $ {
    public static <DATA, T extends Interaction<DATA>> T cast(Class<T> interactive, DATA data) {
        try {
            if (!interactive.isInterface()) {
                throw new IllegalArgumentException("Class<T> interactive should be A Interface, Actually Class is [" + interactive.getName() + "]");
            }
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setInterfaces(new Class[]{interactive});
            T role = interactive.cast(proxyFactory.create(new Class[0], new Object[0], new DomainEventWorkerV2(data, interactive)));
            return role;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
