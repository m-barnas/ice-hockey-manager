package cz.muni.fi.pa165.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when 3rd party authentication algorithms fail.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class ManagerAuthenticationException extends AuthenticationException {

    public ManagerAuthenticationException(String message) {super(message);}

    public ManagerAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
