package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.exceptions.HumanPlayerAuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link HumanPlayerService}'s implementation.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Service
public class HumanPlayerServiceImpl implements HumanPlayerService {


    @Override
    public void register(HumanPlayer humanPlayer, String unencryptedPassword) throws HumanPlayerAuthenticationException {

    }

    @Override
    public boolean authenticate(HumanPlayer humanPlayer, String unencryptedPassword) throws HumanPlayerAuthenticationException {
        return false;
    }

    @Override
    public boolean isAdmin(HumanPlayer humanPlayer) {
        return false;
    }

    @Override
    public HumanPlayer update(HumanPlayer humanPlayer) {
        return null;
    }

    @Override
    public void delete(HumanPlayer humanPlayer) {

    }

    @Override
    public HumanPlayer findById(Long id) {
        return null;
    }

    @Override
    public HumanPlayer findByEmail(String email) {
        return null;
    }

    @Override
    public HumanPlayer findByUsername(String username) {
        return null;
    }

    @Override
    public List<HumanPlayer> findAll() {
        return null;
    }
}
