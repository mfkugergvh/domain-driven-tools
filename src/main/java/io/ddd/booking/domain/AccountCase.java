package io.ddd.booking.domain;

import io.ddd.core.Context;
import io.ddd.core.tools.$;

import java.util.List;

public abstract class AccountCase implements Context {
    @Override
    public void execute() throws Exception {
        Party party  = new Party();
        party.setId("b");
        Person person = $.cast(Person.class,party);
        person.speek("a");
    }

    public static void main(String[] args) throws Exception {
        new AccountCase(){}.execute();
    }
}
