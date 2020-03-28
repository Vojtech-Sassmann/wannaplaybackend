package cz.muni.pv112.wannaplaybackend.service;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class PartyNotExists extends RuntimeException {
    public PartyNotExists() {
        super();
    }

    public PartyNotExists(String message) {
        super(message);
    }

    public PartyNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public PartyNotExists(Throwable cause) {
        super(cause);
    }

    protected PartyNotExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
