package cz.muni.fi.pa165.rest.utils;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.exceptions.ManagerAuthenticationException;

import java.util.Objects;

/**
 * Utility class for managing resource access.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public final class ResourceAccessUtils {

    // hide constructor for utility class
    private ResourceAccessUtils() {}

    private static boolean areEqual(Long first, Long second) {
        return first != null && first.equals(second);
    }

    public static void verifyLoggedHumanPlayer(HumanPlayerDto loggedHumanPlayer, Long id) {
        if (!Objects.equals(loggedHumanPlayer.getId(), id)) {
            throw new ManagerAuthenticationException("Access is denied!");
        }
    }
}
