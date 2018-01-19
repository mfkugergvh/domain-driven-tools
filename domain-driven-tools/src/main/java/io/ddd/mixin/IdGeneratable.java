package io.ddd.mixin;

import io.ddd.core.event.MailBox;
import io.ddd.core.event.provide.ProvideEvent;

import java.util.concurrent.ExecutionException;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2018/1/8.
 */
public interface IdGeneratable {

    default String generateId() {
        ProvideEvent provideEvent = new ProvideEvent(String.class);
        MailBox.get().post(provideEvent);
        try {
            return (String) provideEvent.getProvideValueFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
