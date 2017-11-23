package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.exceptions.HumanPlayerAuthenticationException;

import java.util.List;

/**
 * Define service access to the {@link HumanPlayer} entity.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public interface HumanPlayerService {

    /**
     * Register {@link HumanPlayer} with given unencrypted password.
     *
     * @param humanPlayer to register
     * @param unencryptedPassword human player's unencrypted password
     */
    void register(HumanPlayer humanPlayer, String unencryptedPassword)
            throws HumanPlayerAuthenticationException;

    /**
     * Authenticate {@link HumanPlayer}.
     *
     * @return true only if hashed unencryptedPassword is equal with human player's hashed password
     */
    boolean authenticate(HumanPlayer humanPlayer, String unencryptedPassword) throws HumanPlayerAuthenticationException;

    /**
     * Check whether {@link HumanPlayer} is admin, or not.
     *
     * @param humanPlayer to check
     * @return true if human player is admin, false otherwise
     */
    boolean isAdmin(HumanPlayer humanPlayer);

    /**
     * Update {@link HumanPlayer}.
     *
     * @param humanPlayer to update
     * @return updated human player
     */
    HumanPlayer update(HumanPlayer humanPlayer);

    /**
     * Delete {@link HumanPlayer}.
     *
     * @param humanPlayer to delete
     */
    void delete(HumanPlayer humanPlayer);

    /**
     * Find {@link HumanPlayer} by id.
     *
     * @param id human player's identifier
     * @return human player with given id
     */
    HumanPlayer findById(Long id);

    /**
     * Find {@link HumanPlayer} by email.
     *
     * @param email human player's email
     * @return human player with given email
     */
    HumanPlayer findByEmail(String email);

    /**
     * Find {@link HumanPlayer} by username.
     *
     * @param username human player's username
     * @return human player with given username
     */
    HumanPlayer findByUsername(String username);

    /**
     * Find all {@link HumanPlayer}s.
     *
     * @return list of human players.
     */
    List<HumanPlayer> findAll();

}
