package io.ddd.core.event.invoke.annotation;

import java.lang.annotation.*;

/**
 * {@link javax.annotation.concurrent.ThreadSafe} Annotated Method Must be Thread Safe
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnInvoke {
    int position() default 1;

    Class<?> value();

    boolean allowGeneric() default true;

    String methodName() default "*";
}
