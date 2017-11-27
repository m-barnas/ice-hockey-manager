package cz.muni.fi.pa165.exceptions;

/**
 * Exception thrown when 3rd party authentication algorithms fail.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
