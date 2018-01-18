package io.ddd.core.tools;

import io.ddd.core.Interaction;
import io.ddd.core.delegate.Delegate;
import org.junit.Test;

import java.util.function.Function;

public class $Test {
    public static void main(String[] args) {
        Worker worker = new Worker("1");
        IWorker IWorker = $.cast(IWorker.class, worker);
        IWorker.say();
    }

    public static class Worker {
        String id;

        public Worker(String id) {
            this.id = id;
        }
    }

    public interface IWorker extends Interaction<Worker> {
        default void say() {
            System.out.println(that().id);
        }
    }

    @Test
    public void case001_useTools() {
        IWorker IWorker = $.cast(IWorker.class, new Worker("A"));
        IWorker.say();
    }
}