package io.ddd.booking.domain;

import io.ddd.core.Interaction;

import java.math.BigDecimal;
import java.util.function.Consumer;

public interface Account extends Interaction<Party> {

    void credit(BigDecimal amount, Consumer<AccountFlow> processor);

    void debt(BigDecimal amount, Consumer<AccountFlow> processor);


    void keeping(KeepingData keepingData);

    default void say() {
        System.out.println($().getId());
    }

    Party $();
}
