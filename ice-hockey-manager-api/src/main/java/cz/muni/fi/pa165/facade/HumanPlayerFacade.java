package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.exceptions.HumanPlayerAuthenticationException;

import java.util.Collection;

public interface HumanPlayerFacade {

    /**
     * Register {@link HumanPlayerDto} with given unencrypted password.
     *
     * @param humanPlayerDto to register
     * @param unencryptedPassword human player's unencrypted password
     */
    void register(HumanPlayerDto humanPlayerDto, String unencryptedPassword)
            throws HumanPlayerAuthenticationException;

    /**
     * Authenticate {@link HumanPlayerDto}.
     *
     * @return true only if hashed unencryptedPassword is equal with human player's hashed password
     */
    boolean authenticate(HumanPlayerDto humanPlayerDto, String unencryptedPassword) throws HumanPlayerAuthenticationException;

    /**
     * Check whether {@link HumanPlayerDto} is admin, or not.
     *
     * @return true if human player is admin, false otherwise
     */
    boolean isAdmin(HumanPlayerDto humanPlayerDto);

    /**
     * Update {@link HumanPlayerDto}.
     *
     * @param humanPlayerDto to update
     * @return updated human player
     */
    HumanPlayerDto update(HumanPlayerDto humanPlayerDto);

    /**
     * Delete {@link HumanPlayerDto}.
     *
     * @param humanPlayerDto to delete
     */
    void delete(HumanPlayerDto humanPlayerDto);

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
    Collection<HumanPlayerDto> findAll();
}
