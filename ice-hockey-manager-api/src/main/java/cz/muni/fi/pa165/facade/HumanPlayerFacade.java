package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDTO;
import cz.muni.fi.pa165.dto.HumanPlayerDTO;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;

import java.util.List;

public interface HumanPlayerFacade {

    /**
     * Register {@link HumanPlayerDTO} with given unencrypted password.
     *
     * @param humanPlayerDto to register
     * @param unencryptedPassword human player's unencrypted password
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    void register(HumanPlayerDTO humanPlayerDto, String unencryptedPassword)
            throws AuthenticationException;

    /**
     * Authenticate {@link HumanPlayerDTO}.
     *
     * @param authenticateDTO human player's credentials to authenticate
     * @return true only if hashed unencryptedPassword is equal with human player's hashed password
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    boolean authenticate(HumanPlayerAuthenticateDTO authenticateDTO)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayerDTO}'s password
     *
     * @param humanPlayerId human player identifier
     * @param oldUnencryptedPassword to be change
     * @param newUnencryptedPassword to be set
     * @return updated human player
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    HumanPlayerDTO changePassword(Long humanPlayerId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayerDTO}'s role.
     *
     * @param humanPlayerId human player's identifier
     * @param role to be set
     * @return updated human player
     */
    HumanPlayerDTO changeRole(Long humanPlayerId, Role role);

    /**
     * Delete {@link HumanPlayerDTO}.
     *
     * @param humanPlayerId human player's identifier
     */
    void delete(Long humanPlayerId);

    /**
     * Find {@link HumanPlayerDTO} by id.
     *
     * @param id human player's identifier
     * @return human player with given id
     */
    HumanPlayerDTO findById(Long id);

    /**
     * Find {@link HumanPlayerDTO} by email.
     *
     * @param email human player's email
     * @return human player with given email
     */
    HumanPlayerDTO findByEmail(String email);

    /**
     * Find {@link HumanPlayerDTO} by username.
     *
     * @param username human player's username
     * @return human player with given username
     */
    HumanPlayerDTO findByUsername(String username);

    /**
     * Find all {@link HumanPlayerDTO}s.
     *
     * @return list of human players.
     */
    List<HumanPlayerDTO> findAll();
}
