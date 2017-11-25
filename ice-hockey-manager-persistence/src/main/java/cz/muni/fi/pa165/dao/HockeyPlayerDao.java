package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
public interface HockeyPlayerDao {

	/**
	 * Create {@link HockeyPlayer}
	 *
	 * @param hockeyPlayer to create
	 */
	void create(HockeyPlayer hockeyPlayer);

	/**
	 * Update {@link HockeyPlayer}
	 *
	 * @param hockeyPlayer to update
	 * @return updated hockey player
	 */
	HockeyPlayer update(HockeyPlayer hockeyPlayer);

	/**
	 * Delete {@link HockeyPlayer}
	 *
	 * @param hockeyPlayer to delete
	 */
	void delete(HockeyPlayer hockeyPlayer);

	/**
	 * Find {@link HockeyPlayer} by id.
	 *
	 * @param id to find
	 * @return hockey player with given id
	 */
	HockeyPlayer findById(Long id);

	/**
	 * Find {@link HockeyPlayer} by name.
	 *
	 * @param name to find.
	 * @return hockey player with given name.
	 */
	HockeyPlayer findByName(String name);

	/**
	 * Find {@link HockeyPlayer}s by post.
	 *
	 * @param post to find.
	 * @return list of hockey players with given post.
	 */
	List<HockeyPlayer> findByPost(Position post);

	/**
	 * Find {@link HockeyPlayer}s by team.
	 *
	 * Free agents (hockey players without team have null team)
	 *
	 * @param team to find (null for free agents).
	 * @return list of hockey players of the given team.
	 */
	List<HockeyPlayer> findByTeam(Team team);

	/**
	 * Find {@link HockeyPlayer}s by price.
	 *
	 * @param price maximum price to find (including the border).
	 * @return list of hockey players with price less or equal than the given price.
	 */
	List<HockeyPlayer> findByPriceLessOrEqualThan(BigDecimal price);

	/**
	 * Find all {@link HockeyPlayer}s.
	 *
	 * @return all hockey players
	 */
	List<HockeyPlayer> findAll();
}
