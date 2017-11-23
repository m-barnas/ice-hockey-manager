package cz.muni.fi.pa165.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception thrown when any error occurs during human player's authentication.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class HumanPlayerAuthenticationException extends AuthenticationException {

    public HumanPlayerAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public HumanPlayerAuthenticationException(String msg) {
        super(msg);
    }
}
