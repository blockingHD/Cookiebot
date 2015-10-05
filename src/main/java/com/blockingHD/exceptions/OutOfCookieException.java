package com.blockingHD.exceptions;

/**
 * Created by Mathieu on 5/10/2015.
 */
public class OutOfCookieException extends Exception {
    public OutOfCookieException() {
        super();
    }

    public OutOfCookieException(String message) {
        super(message);
    }

    public OutOfCookieException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfCookieException(Throwable cause) {
        super(cause);
    }

    protected OutOfCookieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
