package io.ddd.core.tools;

import io.ddd.core.Interaction;
import org.junit.Test;

import java.util.function.Function;

public class $Test {

    public static class WorkerData {
        String id;

        public WorkerData(String id) {
            this.id = id;
        }
    }

    public static interface Worker extends Interaction<WorkerData> {
        public default void say() {
            System.out.println(that().id);
        }
    }
    Function<String,String> function;

    @Test
    public void case001_useTools() throws Exception {
        Worker worker = $.cast(Worker.class, new WorkerData("A"));
        worker.say();
    }
}