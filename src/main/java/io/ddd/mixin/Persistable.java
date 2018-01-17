package io.ddd.mixin;

import io.ddd.core.event.MailBox;
import io.ddd.core.event.persist.PersistEvent;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2018/1/5.
 */
@ThreadSafe
public interface Persistable {


    default void update(Object object) throws Exception {
        PersistEvent persistEvent = new PersistEvent(object, object.getClass(), PersistEvent.CRUD.U, new CompletableFuture());
        MailBox.get().post(persistEvent);
        waiting(persistEvent);
    }

    static void waiting(PersistEvent persistEvent) throws Exception {
        try {
            persistEvent.getCompletableFuture().get();
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            if (e.getCause() instanceof Exception) {
                throw ((Exception) e.getCause());
            } else {
                throw e;
            }
        }
    }

    default void insert(Object object) throws Exception {
        PersistEvent persistEvent = new PersistEvent(object, object.getClass(), PersistEvent.CRUD.C, new CompletableFuture());
        MailBox.get().post(persistEvent);
        waiting(persistEvent);
    }

    default void delete(Object object) throws Exception {
        PersistEvent persistEvent = new PersistEvent(object, object.getClass(), PersistEvent.CRUD.D, new CompletableFuture());
        MailBox.get().post(persistEvent);
        waiting(persistEvent);
    }

    default void upsert(Object object) throws Exception {
        PersistEvent persistEvent = new PersistEvent(object, object.getClass(), PersistEvent.CRUD.CU, new CompletableFuture());
        MailBox.get().post(persistEvent);
        waiting(persistEvent);
    }
}
