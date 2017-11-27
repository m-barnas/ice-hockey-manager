package cz.muni.fi.pa165.service.exceptions;

/**
 * Exception is throw if human player does not exist in the system.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class NoSuchHumanPlayerException extends IllegalArgumentException {

    public NoSuchHumanPlayerException(String message) {
        super(message);
    }
}
