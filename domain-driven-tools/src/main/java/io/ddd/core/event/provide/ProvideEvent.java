package io.ddd.core.event.provide;

import io.ddd.core.event.AbstractEvent;

import java.util.concurrent.CompletableFuture;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2018/1/8.
 */
public class ProvideEvent extends AbstractEvent {

    private final String provideQualifier;

    private final Class<?> provideValueType;

    private final CompletableFuture provideValueFuture = new CompletableFuture();

    public ProvideEvent(Class<?> provideValueType) {
        this(provideValueType.getSimpleName(), provideValueType);
    }


    public ProvideEvent(String provideQualifier, Class<?> provideValueType) {
        this.provideQualifier = provideQualifier;
        this.provideValueType = provideValueType;
        super.getCompletableFuture().whenComplete((o, o2) -> {
            if (!provideValueFuture.isDone()) {
                provideValueFuture.complete(null);
            }
        });
    }


    public String getProvideQualifier() {
        return provideQualifier;
    }

    public Class<?> getProvideValueType() {
        return provideValueType;
    }

    public CompletableFuture getProvideValueFuture() {
        return provideValueFuture;
    }

    @Override
    public CompletableFuture getCompletableFuture() {
        return super.getCompletableFuture();
    }
}
