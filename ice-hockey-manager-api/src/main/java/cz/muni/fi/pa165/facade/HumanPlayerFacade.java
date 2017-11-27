package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDto;
import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;

import java.util.List;

public interface HumanPlayerFacade {

    /**
     * Register {@link HumanPlayerDto} with given unencrypted password.
     *
     * @param humanPlayerDto to register
     * @param unencryptedPassword human player's unencrypted password
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    void register(HumanPlayerDto humanPlayerDto, String unencryptedPassword)
            throws AuthenticationException;

    /**
     * Authenticate {@link HumanPlayerDto}.
     *
     * @param authenticateDTO human player's credentials to authenticate
     * @return true only if hashed unencryptedPassword is equal with human player's hashed password
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    boolean authenticate(HumanPlayerAuthenticateDto authenticateDTO)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayerDto}'s password
     *
     * @param humanPlayerId human player identifier
     * @param oldUnencryptedPassword to be change
     * @param newUnencryptedPassword to be set
     * @return updated human player
     * @throws AuthenticationException when 3rd party authentication algorithms fail
     */
    HumanPlayerDto changePassword(Long humanPlayerId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws AuthenticationException;

    /**
     * Change {@link HumanPlayerDto}'s role.
     *
     * @param humanPlayerId human player's identifier
     * @param role to be set
     * @return updated human player
     */
    HumanPlayerDto changeRole(Long humanPlayerId, Role role);

    /**
     * Delete {@link HumanPlayerDto}.
     *
     * @param humanPlayerId human player's identifier
     */
    void delete(Long humanPlayerId);

    /**
     * Find {@link HumanPlayerDto} by id.
     *
     * @param id human player's identifier
     * @return human player with given id
     */
    HumanPlayerDto findById(Long id);

    /**
     * Find {@link HumanPlayerDto} by email.
     *
     * @param email human player's email
     * @return human player with given email
     */
    HumanPlayerDto findByEmail(String email);

    /**
     * Find {@link HumanPlayerDto} by username.
     *
     * @param username human player's username
     * @return human player with given username
     */
    HumanPlayerDto findByUsername(String username);

    /**
     * Find all {@link HumanPlayerDto}s.
     *
     * @return list of human players.
     */
    List<HumanPlayerDto> findAll();
}
