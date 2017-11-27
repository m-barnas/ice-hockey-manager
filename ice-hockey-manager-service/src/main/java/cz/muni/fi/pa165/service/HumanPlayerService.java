package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;

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
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    void register(HumanPlayer humanPlayer, String unencryptedPassword)
            throws AuthenticationException;

    /**
     * Authenticate {@link HumanPlayer}.
     *
     * @return true only if hashed unencryptedPassword is equal with human player's hashed password
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    boolean authenticate(HumanPlayer humanPlayer, String unencryptedPassword)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayer}'s password
     *
     * @param humanPlayerId human player identifier
     * @param oldUnencryptedPassword to be change
     * @param newUnencryptedPassword to be set
     * @return updated human player
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    HumanPlayer changePassword(Long humanPlayerId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayer}'s role.
     *
     * @param humanPlayerId human player's identifier
     * @param role to be set
     * @return updated human player
     */
    HumanPlayer changeRole(Long humanPlayerId, Role role);

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
