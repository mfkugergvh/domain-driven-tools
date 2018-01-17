package io.ddd.booking.domain;

import io.ddd.core.event.DomainEventWorker;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setUseWriteReplace(true);
        proxyFactory.setSuperclass(Party.class);
        proxyFactory.setInterfaces(new Class[]{Account.class});
        Party role = Party.class.cast(proxyFactory.create(new Class[0], new Object[0], new DomainEventWorker()));
        role.setId("a");
        ((Account) role).say();
    }
}
