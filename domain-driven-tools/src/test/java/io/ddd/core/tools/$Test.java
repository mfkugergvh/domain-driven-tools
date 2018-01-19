package io.ddd.core.tools;

import io.ddd.core.Interaction;
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

        default Worker indroduce(String name, String type, Function<NameAndType, Worker> findByNameAndType) {
            return findByNameAndType.apply(new NameAndType(name, type));
        }

        default void say() {
            System.out.println(that().id);
        }

        class NameAndType {
            public final String name;
            public final String type;

            public NameAndType(String name, String type) {
                this.name = name;
                this.type = type;
            }
        }
    }

    @Test
    public void case001_useTools() {
        IWorker iWorker = $.cast(IWorker.class, new Worker("A"));
        iWorker.say();
        Worker newWorker  = iWorker.indroduce("a", "b", $ -> new Worker($.name + $.type));
        IWorker newIWorker = $.cast(IWorker.class,newWorker);
        newIWorker.say();
    }
}