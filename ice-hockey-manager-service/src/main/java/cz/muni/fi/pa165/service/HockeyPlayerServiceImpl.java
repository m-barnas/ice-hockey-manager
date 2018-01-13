package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.HockeyPlayerDao;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.exceptions.HockeyPlayerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link HockeyPlayerService} implementation.
 *
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@Service
public class HockeyPlayerServiceImpl implements HockeyPlayerService {

	@Autowired
	private HockeyPlayerDao playerDao;

	@Override
	public Long create(HockeyPlayer player) {
		validate(player);
		playerDao.create(player);
		return player.getId();
	}

	@Override
	public HockeyPlayer update(HockeyPlayer player) {
		return playerDao.update(player);
	}

	@Override
	public void delete(HockeyPlayer player) {
		playerDao.delete(player);
	}

	@Override
	public HockeyPlayer findById(Long id) {
		return playerDao.findById(id);
	}

	@Override
	public HockeyPlayer findByName(String name) {
		return playerDao.findByName(name);
	}

	@Override
	public List<HockeyPlayer> findByPost(Position post) {
		return playerDao.findByPost(post);
	}

	@Override
	public List<HockeyPlayer> findByTeam(Team team) {
		return playerDao.findByTeam(team);
	}

	@Override
	public List<HockeyPlayer> findByPriceLessOrEqualThan(BigDecimal maxPrice) {
		return playerDao.findByPriceLessOrEqualThan(maxPrice);
	}

	@Override
	public List<HockeyPlayer> findAll() {
		return playerDao.findAll();
	}

	@Override
	public List<HockeyPlayer> findFreeAgents() {
		return playerDao.findFreeAgents();
	}

	@Override
	public List<HockeyPlayer> findByPostAndPrice(Position post, BigDecimal maxPrice) {
		List<HockeyPlayer> players = playerDao.findByPriceLessOrEqualThan(maxPrice);
		List<HockeyPlayer> filteredPlayers = new ArrayList<>();
		for (HockeyPlayer player : players) {
			if (player.getPost() == post) {
				filteredPlayers.add(player);
			}
		}
		return filteredPlayers;
	}

	@Override
	public List<HockeyPlayer> findByAttSkill(int minAttSkill) {
		if (minAttSkill < 1 || minAttSkill > 99) {
			throw new HockeyPlayerServiceException("Attack skill must be between 1 and 99");
		}
		List<HockeyPlayer> players = playerDao.findAll();
		List<HockeyPlayer> filteredPlayers = new ArrayList<>();
		for (HockeyPlayer player : players) {
			if (player.getAttackSkill() >= minAttSkill) {
				filteredPlayers.add(player);
			}
		}
		return filteredPlayers;
	}

	@Override
	public List<HockeyPlayer> findByDefSkill(int minDefSkill) {
		if (minDefSkill < 1 || minDefSkill > 99) {
			throw new HockeyPlayerServiceException("Defensive skill must be between 1 and 99");
		}
		List<HockeyPlayer> players = playerDao.findAll();
		List<HockeyPlayer> filteredPlayers = new ArrayList<>();
		for (HockeyPlayer player : players) {
			if (player.getDefenseSkill() >= minDefSkill) {
				filteredPlayers.add(player);
			}
		}
		return filteredPlayers;
	}

	private void validate(HockeyPlayer player) {
		if (player == null) {
			throw new IllegalArgumentException("Player is null.");
		}
		if (player.getName() == null) {
			throw new IllegalArgumentException("Player's name is null");
		}
		if (player.getPost() == null) {
			throw new IllegalArgumentException("Player's post is null");
		}
		if (player.getAttackSkill() < 1 || player.getAttackSkill() > 99) {
			throw new IllegalArgumentException("Invalid attack skill");
		}
		if (player.getDefenseSkill() < 1 || player.getDefenseSkill() > 99) {
			throw new IllegalArgumentException("Invalid defense skill");
		}
		if (player.getPrice() == null || player.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Invalid price");
		}
	}
}
