package io.ddd.core;

import io.ddd.core.tools.$;

public interface Context {

    default <DATA, T extends Interaction<DATA>> T $(Class<T> tClass, DATA data) {
        return $.cast(tClass, data);
    }


}
