package cz.muni.pv112.wannaplaybackend.service.exceptions;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class EventNotExistsException extends RuntimeException {
    public EventNotExistsException() {
        super();
    }

    public EventNotExistsException(String message) {
        super(message);
    }

    public EventNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventNotExistsException(Throwable cause) {
        super(cause);
    }

    protected EventNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
