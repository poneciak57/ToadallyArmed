package org.toadallyarmed.exception;

import org.toadallyarmed.util.logger.Logger;

public class NotBaseComponentException extends RuntimeException {
    public NotBaseComponentException(String message) {
        super(message);
        Logger.error("NotBaseComponentException: " + message);
    }
}
