package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@Repository
public class HockeyPlayerDaoImpl implements HockeyPlayerDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(HockeyPlayer hockeyPlayer) {
		em.persist(hockeyPlayer);
	}

	@Override
	public HockeyPlayer update(HockeyPlayer hockeyPlayer) {
		return em.merge(hockeyPlayer);
	}

	@Override
	public void delete(HockeyPlayer hockeyPlayer) {
		em.remove(hockeyPlayer);
	}

	@Override
	public HockeyPlayer findById(Long id) {
		return em.find(HockeyPlayer.class, id);
	}

	@Override
	public HockeyPlayer findByName(String name) {
		List<HockeyPlayer> hockeyPlayers = em.createQuery("select h from HockeyPlayer h where h.name = :name",
				HockeyPlayer.class)
				.setMaxResults(1)
				.setParameter("name", name)
				.getResultList();
		return hockeyPlayers.isEmpty() ? null : hockeyPlayers.get(0);
	}

	@Override
	public List<HockeyPlayer> findByPost(Position post) {
		return em.createQuery("select h from HockeyPlayer h where h.post = :post",
				HockeyPlayer.class)
				.setParameter("post", post)
				.getResultList();
	}

	@Override
	public List<HockeyPlayer> findByTeam(Team team) {
		return em.createQuery("select h from HockeyPlayer h where h.team = :team",
				HockeyPlayer.class)
				.setParameter("team", team)
				.getResultList();
	}

	@Override
	public List<HockeyPlayer> findByPriceIsLessThan(BigDecimal price) {
		return em.createQuery("select h from HockeyPlayer h where h.price < :price",
				HockeyPlayer.class)
				.setParameter("price", price)
				.getResultList();
	}

	@Override
	public List<HockeyPlayer> findAll() {
		return em.createQuery("select h from HockeyPlayer h", HockeyPlayer.class)
				.getResultList();
	}
}
