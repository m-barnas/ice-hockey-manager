package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of GameDao class.
 *
 * @author Marketa Elederova
 */
@Repository
public class GameDaoImpl implements GameDao {

    @Autowired
	private EntityManager em;

    @Override
    public void create(Game game) {
        em.persist(game);
    }

    @Override
    public Game update(Game game) {
        return em.merge(game);
    }

    @Override
    public void delete(Game game) {
        em.remove(em.merge(game));
    }

    @Override
    public Game findById(Long id) {
        return em.find(Game.class, id);
    }

    @Override
    public Game findByFirstTeamAndSecondTeamAndStartTime(Team firstTeam, Team secondTeam, LocalDateTime startTime) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> query = cb.createQuery(Game.class);
		Root<Game> g = query.from(Game.class);
		query.select(g).where(cb.and(
                cb.equal(g.get("firstTeam"), firstTeam),
                cb.equal(g.get("secondTeam"), secondTeam),
                cb.equal(g.get("startTime"), startTime)
        ));
		return em.createQuery(query).getSingleResult();
    }

    @Override
    public List<Game> findByTeam(Team team) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Game> query = cb.createQuery(Game.class);
		Root<Game> g = query.from(Game.class);
		query.select(g).where(cb.or(
                cb.equal(g.get("firstTeam"), team),
                cb.equal(g.get("secondTeam"), team)
        ));
		return em.createQuery(query).getResultList();
    }

    @Override
    public List<Game> findAll() {
        return em.createQuery("select g from Game g", Game.class)
					.getResultList();
    }
}
