package io.ddd.booking.domain;

import io.ddd.core.Interaction;

public interface Person extends Interaction<Party> {
    default void speek(String a) {
        System.out.println(that().getId() + " say : " + a);
    }
}
