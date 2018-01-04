package cz.muni.fi.pa165.service;

import com.google.common.base.Preconditions;
import cz.muni.fi.pa165.dao.HumanPlayerDao;
import cz.muni.fi.pa165.dao.TeamDao;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.ManagerAuthenticationException;
import cz.muni.fi.pa165.service.exceptions.NoSuchHumanPlayerException;
import cz.muni.fi.pa165.service.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link HumanPlayerService}'s implementation.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Service
public class HumanPlayerServiceImpl implements HumanPlayerService {

    @Autowired
    private HumanPlayerDao humanPlayerDao;

    @Autowired
    private TeamDao teamDao;

    @Override
    public void register(HumanPlayer humanPlayer, String unencryptedPassword)
            throws ManagerAuthenticationException {
        // preconditions
        Preconditions.checkNotNull(humanPlayer, "Human player must not be null.");
        Preconditions.checkNotNull(unencryptedPassword, "Unencrypted password must not be null.");
        AuthenticationUtils.checkPasswordLength(unencryptedPassword);

        // save user
        humanPlayer.setPasswordHash(AuthenticationUtils.createHash(unencryptedPassword));
        humanPlayerDao.create(humanPlayer);
    }

    @Override
    public boolean authenticate(HumanPlayer humanPlayer, String unencryptedPassword)
            throws ManagerAuthenticationException {
        // preconditions
        Preconditions.checkNotNull(humanPlayer, "Human player must not be null.");

        // verify unencryptedPassword
        return AuthenticationUtils.verifyPassword(unencryptedPassword, humanPlayer.getPasswordHash());
    }

    @Override
    public HumanPlayer changePassword(Long humanPlayerId, String oldUnencryptedPassword, String newUnencryptedPassword)
        throws ManagerAuthenticationException {
        // preconditions
        Preconditions.checkNotNull(humanPlayerId, "Human player's id must not be null.");
        Preconditions.checkNotNull(oldUnencryptedPassword, "Old unencrypted password must not be null.");
        Preconditions.checkNotNull(newUnencryptedPassword, "New unencrypted password must not be null.");
        AuthenticationUtils.checkPasswordLength(newUnencryptedPassword);

        // find human player
        HumanPlayer humanPlayer = humanPlayerDao.findById(humanPlayerId);
        if (humanPlayer == null) {
            throw new NoSuchHumanPlayerException("Human player with id " + humanPlayerId + " does not exist.");
        }

        // verify password
        if (AuthenticationUtils.verifyPassword(oldUnencryptedPassword, humanPlayer.getPasswordHash())) {
            humanPlayer.setPasswordHash(AuthenticationUtils.createHash(newUnencryptedPassword));
            return humanPlayerDao.update(humanPlayer);
        }
        return humanPlayer;
    }

    @Override
    public HumanPlayer changeRole(Long humanPlayerId, Role role) {
        // preconditions
        Preconditions.checkNotNull(humanPlayerId, "Human player's id must not be null.");
        Preconditions.checkNotNull(role, "Role must not be null.");

        // find human player
        HumanPlayer humanPlayer = humanPlayerDao.findById(humanPlayerId);
        if (humanPlayer == null) {
            throw new NoSuchHumanPlayerException("Human player with id " + humanPlayerId + " does not exist.");
        }

        humanPlayer.setRole(role);
        return humanPlayerDao.update(humanPlayer);
    }

    @Override
    public void delete(HumanPlayer humanPlayer) {
        Team team = teamDao.findByHumanPlayer(humanPlayer);
        if (team != null) {
            team.setHumanPlayer(null);
            teamDao.update(team);
        }
        humanPlayerDao.delete(humanPlayer);
    }

    @Override
    public HumanPlayer findById(Long id) {
        return humanPlayerDao.findById(id);
    }

    @Override
    public HumanPlayer findByEmail(String email) {
        return humanPlayerDao.findByEmail(email);
    }

    @Override
    public HumanPlayer findByUsername(String username) {
        return humanPlayerDao.findByUsername(username);
    }

    @Override
    public List<HumanPlayer> findAll() {
        return humanPlayerDao.findAll();
    }
}
