package cz.muni.pv112.wannaplaybackend.service.exceptions;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class UserNotMemberOfPartyException extends RuntimeException {
    public UserNotMemberOfPartyException() {
        super();
    }

    public UserNotMemberOfPartyException(String message) {
        super(message);
    }

    public UserNotMemberOfPartyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotMemberOfPartyException(Throwable cause) {
        super(cause);
    }

    protected UserNotMemberOfPartyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
