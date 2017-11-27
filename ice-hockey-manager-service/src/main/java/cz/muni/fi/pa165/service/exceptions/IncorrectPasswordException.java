package cz.muni.fi.pa165.service.exceptions;

/**
 * Exception thrown when incorrect password is submitted.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class IncorrectPasswordException extends IllegalArgumentException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
