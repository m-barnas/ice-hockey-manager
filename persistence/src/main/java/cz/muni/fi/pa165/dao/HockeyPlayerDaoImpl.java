package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.repositories.HockeyPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@Repository
public class HockeyPlayerDaoImpl implements HockeyPlayerDao {

	@Autowired
	private HockeyPlayerRepository repository;

	@Override
	public HockeyPlayer save(HockeyPlayer hockeyPlayer) {
		return repository.save(hockeyPlayer);
	}

	@Override
	public void delete(HockeyPlayer hockeyPlayer) {
		repository.delete(hockeyPlayer);
	}

	@Override
	public HockeyPlayer findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public HockeyPlayer findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<HockeyPlayer> findByPost(Position post) {
		return repository.findByPost(post);
	}

	@Override
	public List<HockeyPlayer> findByTeam(Team team) {
		return repository.findByTeam(team);
	}

	@Override
	public List<HockeyPlayer> findByPriceIsLessThan(BigDecimal price) {
		return repository.findByPriceIsLessThanEqual(price);
	}

	@Override
	public List<HockeyPlayer> findAll() {
		return repository.findAll();
	}
}
