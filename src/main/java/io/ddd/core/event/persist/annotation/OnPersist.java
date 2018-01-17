package io.ddd.core.event.persist.annotation;

import io.ddd.core.action.Mode;

import java.lang.annotation.*;

/**
 * {@link javax.annotation.concurrent.ThreadSafe} Annotated Method Must be Thread Safe
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnPersist {
    boolean allowGenericType() default false;

    Mode mode() default Mode.ANY;
}
