package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HumanPlayer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Repository
public class HumanPlayerDaoImpl implements HumanPlayerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(HumanPlayer humanPlayer) {
        em.persist(humanPlayer);
    }

    @Override
    public HumanPlayer update(HumanPlayer humanPlayer) {
        return em.merge(humanPlayer);
    }

    @Override
    public void delete(HumanPlayer humanPlayer) {
        em.remove(humanPlayer);
    }

    @Override
    public HumanPlayer findById(Long id) {
        return em.find(HumanPlayer.class, id);
    }

    @Override
    public HumanPlayer findByEmail(String email) {
        List<HumanPlayer> humanPlayers = em.createQuery("select h from HumanPlayer h WHERE lower(h.email) = :email",
                HumanPlayer.class)
                .setMaxResults(1)
                .setParameter("email", email)
                .getResultList();
        return humanPlayers.isEmpty() ? null : humanPlayers.get(0);
    }

    @Override
    public HumanPlayer findByUsername(String username) {
        List<HumanPlayer> humanPlayers = em.createQuery("select h from HumanPlayer h WHERE lower(h.username) = "
                + ":username", HumanPlayer.class)
                .setMaxResults(1)
                .setParameter("username", username)
                .getResultList();
        return humanPlayers.isEmpty() ? null : humanPlayers.get(0);
    }

    @Override
    public List<HumanPlayer> findAll() {
        return em.createQuery("select h from HumanPlayer h", HumanPlayer.class)
                .getResultList();
    }
}
