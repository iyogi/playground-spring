package com.yrs.common.exceptions;

public class GenericRuntimeException extends RuntimeException {

    private final boolean retryable;

    public GenericRuntimeException(String message) {
        this(message, false);
    }

    public GenericRuntimeException(String message, boolean retryable) {
        super(message);
        this.retryable = retryable;
    }

    public GenericRuntimeException(String message, Throwable cause) {
        this(message, cause, false);
    }

    public GenericRuntimeException(String message, Throwable cause, boolean retryable) {
        super(message, cause);
        this.retryable = retryable;
    }

    public boolean isRetryable() {
        return retryable;
    }

    @Override
    public String toString() {
        return super.toString() + " [retryable=" + retryable + "]";
    }
}
