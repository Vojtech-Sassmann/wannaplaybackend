package cz.muni.pv112.wannaplaybackend.service.exceptions;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class EventFullException extends RuntimeException {
    public EventFullException() {
        super();
    }

    public EventFullException(String message) {
        super(message);
    }

    public EventFullException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventFullException(Throwable cause) {
        super(cause);
    }

    protected EventFullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
