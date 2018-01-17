package io.ddd.core.delegate;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Delegate<T> {
    protected T delegate;

    public T delegate(T t) {
        this.delegate = delegate;
        return (T) this;
    }

    public static class $Consumer<P> extends Delegate<Consumer<P>> {

        public void call(P p) {
            if (delegate == null) {
                throw new NoDelegateExistsException();
            }
            delegate.accept(p);
        }

    }

    public static class $Function<P, R> extends Delegate<Function<P, R>> {

        public R call(P p) {
            if (delegate == null) {
                throw new NoDelegateExistsException();
            }
            return delegate.apply(p);
        }
    }

    public static class $Runnable extends Delegate<Runnable> {

        public void call() {
            if (delegate == null) {
                throw new NoDelegateExistsException();
            }
            delegate.run();
        }
    }

    public static class $Supplier<R> extends Delegate<Supplier<R>> {

        public R call() {
            if (delegate == null) {
                throw new NoDelegateExistsException();
            }
            return delegate.get();
        }
    }
}
