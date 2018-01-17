package io.ddd.core.event.persist;

import io.ddd.core.event.AbstractEvent;

import java.util.concurrent.*;

/**
 * 说明：
 *
 * @author 周靖捷
 * Created by 周靖捷 on 2017/12/29.
 */
public class PersistEvent<DATA> extends AbstractEvent {
    private final DATA data;
    private final Class<DATA> type;
    private final CRUD crud;
    private final Future<DATA> dataFuture;
    private boolean isPersist = false;
    private final CompletableFuture completableFuture = new CompletableFuture();

    public PersistEvent(DATA data, Class<DATA> type, CRUD crud, Future<DATA> dataFuture) {
        this.data = data;
        this.type = type;
        this.crud = crud;
        this.dataFuture = dataFuture;
    }

    public void persist() {
        this.isPersist = true;
    }

    public DATA getData() {
        return data;
    }

    public Class<DATA> getType() {
        return type;
    }

    public CRUD getCrud() {
        return crud;
    }

    public DATA getChanged() throws ExecutionException, InterruptedException {
        return dataFuture.get();
    }

    public DATA getChanged(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        return dataFuture.get(timeout, TimeUnit.MILLISECONDS);
    }

    public boolean isPersist() {
        return isPersist;
    }

    public CompletableFuture getCompletableFuture() {
        return completableFuture;
    }

    public enum CRUD {
        C,
        R,
        U,
        CU,
        D
    }

}
