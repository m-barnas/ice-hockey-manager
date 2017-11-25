package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.exceptions.HockeyPlayerServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Define service access to the {@link HockeyPlayer} entity.
 *
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
public interface HockeyPlayerService {

	/**
	 * Creates new {@link HockeyPlayer}.
	 *
	 * @param player to create.
	 * @return Created hockey player with new id field.
	 */
	HockeyPlayer create(HockeyPlayer player);

	/**
	 * Deletes {@link HockeyPlayer}.
	 *
	 * @param player to remove.
	 */
	void delete(HockeyPlayer player);

	/**
	 * Returns {@link HockeyPlayer} with given id.
	 *
	 * @param id of returned player.
	 * @return hockey player.
	 */
	HockeyPlayer findById(Long id);

	/**
	 * Returns {@link HockeyPlayer} with given name.
	 *
	 * @param name of returned player.
	 * @return hockey player.
	 */
	HockeyPlayer findByName(String name);

	/**
	 * Returns {@link HockeyPlayer}s on given post.
	 *
	 * @param post of returned players.
	 * @return list of hockey players.
	 */
	List<HockeyPlayer> findByPost(Position post);

	/**
	 * Returns {@link HockeyPlayer}s of given team.
	 *
	 * @param team of returned players.
	 * @return list of hockey players.
	 */
	List<HockeyPlayer> findByTeam(Team team);

	/**
	 * Returns {@link HockeyPlayer}s with price less or equal than given price.
	 *
	 * @param maxPrice maximum price to find (border included)
	 * @return list of hockey players
	 */
	List<HockeyPlayer> findByPriceLessOrEqualThan(BigDecimal maxPrice);

	/**
	 * Returns all {@link HockeyPlayer}s.
	 *
	 * @return all hockey players.
	 */
	List<HockeyPlayer> findAll();

	/**
	 * Returns all free agents ({@link HockeyPlayer}s without a team)
	 *
	 * @return all hockey players without a team
	 */
	List<HockeyPlayer> findFreeAgents();

	/**
	 * Returns {@link HockeyPlayer}s on given post limited by maximal price.
	 *
	 * @param post position to find
	 * @param maxPrice maximum price to find (border included)
	 * @return list of hockey players
	 */
	List<HockeyPlayer> findByPostAndPrice(Position post, BigDecimal maxPrice);

	/**
	 * Returns {@link HockeyPlayer}s with higher attack skill than given limit (border included).
	 *
	 * @param minAttSkill minimal attack skill
	 * @return list of hockey players
	 */
	List<HockeyPlayer> findByAttSkill(int minAttSkill) throws HockeyPlayerServiceException;

	/**
	 * Returns {@link HockeyPlayer}s with higher defense skill than given limit (border included).
	 *
	 * @param minDefSkill minimal defense skill
	 * @return list of hockey players
	 */
	List<HockeyPlayer> findByDefSkill(int minDefSkill) throws HockeyPlayerServiceException;
}
