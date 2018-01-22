package io.ddd.core.exception;

public class DomainLayerException extends RuntimeException {

    private Class onClass;
    private Throwable targetException;

    public DomainLayerException(String message, Object domainObject, Throwable targetException) {
        super(message);
        this.onClass = domainObject.getClass();
        this.targetException = targetException;
    }

    public DomainLayerException(Object domainObject) {
        this(null, domainObject, null);
        this.targetException = this;
    }

    public DomainLayerException(String message, Object domainObject) {
        this(message, domainObject, null);
        this.targetException = this;
    }

    public Class getOnClass() {
        return onClass;
    }

    public Throwable getTargetException() {
        return targetException;
    }
}
