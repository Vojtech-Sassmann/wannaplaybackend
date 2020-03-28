package cz.muni.pv112.wannaplaybackend.service.exceptions;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException() {
    }

    public UserNotExistsException(String message) {
        super(message);
    }

    public UserNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistsException(Throwable cause) {
        super(cause);
    }

    public UserNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
