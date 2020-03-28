package cz.muni.pv112.wannaplaybackend.service.exceptions;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class PartyNotExistsException extends RuntimeException {
    public PartyNotExistsException() {
        super();
    }

    public PartyNotExistsException(String message) {
        super(message);
    }

    public PartyNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PartyNotExistsException(Throwable cause) {
        super(cause);
    }

    protected PartyNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
