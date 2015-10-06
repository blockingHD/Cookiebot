package com.blockingHD.exceptions;

/**
 * Created by MrKickkiller on 5/10/2015.
 */
public class UserNotFoundException extends NullPointerException {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
