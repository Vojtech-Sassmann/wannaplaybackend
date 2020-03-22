package cz.muni.pv112.wannaplaybackend.service;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class UserNotExists extends RuntimeException {
    public UserNotExists() {
    }

    public UserNotExists(String message) {
        super(message);
    }

    public UserNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExists(Throwable cause) {
        super(cause);
    }

    public UserNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
