package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.repositories.HumanPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Repository
public class HumanPlayerDaoImpl implements HumanPlayerDao {

    @Autowired
    private HumanPlayerRepository humanPlayerRepository;

    @Override
    public HumanPlayer save(HumanPlayer humanPlayer) {
        return humanPlayerRepository.save(humanPlayer);
    }

    @Override
    public void delete(HumanPlayer humanPlayer) {
        humanPlayerRepository.delete(humanPlayer);
    }

    @Override
    public HumanPlayer findById(Long id) {
        return humanPlayerRepository.findById(id);
    }

    @Override
    public HumanPlayer findByEmail(String email) {
        return humanPlayerRepository.findByEmail(email);
    }

    @Override
    public HumanPlayer findByUsername(String username) {
        return humanPlayerRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public List<HumanPlayer> findAll() {
        return humanPlayerRepository.findAll();
    }
}
