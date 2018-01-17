package io.ddd.core;

import io.ddd.core.event.DomainEventWorker;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/26.
 */
public interface Context {
    default <DATA, T extends Role<DATA>> T deriveRole(Class<T> interactiveClass, DATA data) {
        try {
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setSuperclass(interactiveClass);
            T role = interactiveClass.cast(proxyFactory.create(new Class[0], new Object[0], new DomainEventWorker()));
            role.actAs(data);
            return role;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    void execute() throws Exception;

    default Context andThen(Context nextContext) {
        Objects.requireNonNull(nextContext);
        return () -> {
            this.execute();
            nextContext.execute();
        };
    }
}
