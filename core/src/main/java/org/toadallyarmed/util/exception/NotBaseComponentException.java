package org.toadallyarmed.util.exception;

import org.toadallyarmed.util.log.Logger;

public class NotBaseComponentException extends RuntimeException {
    public NotBaseComponentException(String message) {
        super(message);
        Logger.error("NotBaseComponentException: " + message);
    }
}
