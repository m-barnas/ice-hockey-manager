package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.enums.Position;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Jakub Hruska jhruska@mail.munil.cz
 */
public interface HockeyPlayerFacade {

	/**
	 * Creates {@link HockeyPlayerDto}.
	 *
	 * @param hockeyPlayerDto to create
	 * @return hockey player's id
	 */
	Long create(HockeyPlayerDto hockeyPlayerDto);

	/**
	 * Deletes {@link HockeyPlayerDto}.
	 *
	 * @param id of player to delete
	 */
	void delete(Long id);

	/**
	 * Updates {@link HockeyPlayerDto}.
	 *
	 * @param hockeyPlayerDto to update
	 * @return updated hockey player
	 */
	HockeyPlayerDto update(HockeyPlayerDto hockeyPlayerDto);
	/**
	 * Returns {@link HockeyPlayerDto} with the given id.
	 *
	 * @param id of the player
	 * @return hockey player
	 */
	HockeyPlayerDto findById(Long id);

	/**
	 * Returns {@link HockeyPlayerDto} with given name (names are unique).
	 *
	 * @param name of the player
	 * @return hockey player
	 */
	HockeyPlayerDto findByName(String name);

	/**
	 * Returns all {@link HockeyPlayerDto}s on given position.
	 *
	 * @param post to get players on
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findByPost(Position post);

	/**
	 * Returns all {@link HockeyPlayerDto}s of the given team.
	 *
	 * @param team to get players from
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findByTeam(TeamDto team);

	/**
	 * Returns {@link HockeyPlayerDto}s with price less or equal as the given maximal price.
	 *
	 * @param maxPrice maximal available price
	 * @return collection of Hockey players
	 */
	Collection<HockeyPlayerDto> findByPriceLessOrEqualThan(BigDecimal maxPrice);

	/**
	 * Returns all {@link HockeyPlayerDto}s.
	 *
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findAll();

	/**
	 * Returns {@link HockeyPlayerDto}s without any team (free agents).
	 *
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findFreeAgents();

	/**
	 * Returns {@link HockeyPlayerDto}s on given post with price less than the given.
	 *
	 * @param post position of the players
	 * @param maxPrice maximal available price
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findByPostAndPrice(Position post, BigDecimal maxPrice);

	/**
	 * Returns {@link HockeyPlayerDto}s with attack skill at least same as given.
	 *
	 * @param minAttSkill minimal attack skill
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findByAttSkill(int minAttSkill);

	/**
	 * Returns {@link HockeyPlayerDto}s with defense skill at least same as given.
	 *
	 * @param minDefSkill minimal defense skill
	 * @return collection of hockey players
	 */
	Collection<HockeyPlayerDto> findByDefSkill(int minDefSkill);
}
