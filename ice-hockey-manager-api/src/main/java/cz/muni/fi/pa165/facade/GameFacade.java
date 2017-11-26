package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import java.util.List;

/**
 * Facade for working with hockey games.
 *
 * @author Marketa Elederova
 */
public interface GameFacade {

    /**
     * Create new game.
     *
     * @param gameCreateDto game to create
     */
    void create(GameCreateDto gameCreateDto);

    /**
     * Delete game from system.
     *
     * @param gameId id of game to delete
     */
    void delete(Long gameId);

    /**
     * Make {@link GameDto} state canceled.
     *
     * @param gameId id of game to cancel
     * @return true if game state was changed
     * @throws IllegalArgumentException if game has been already played (has
     * not null score)
     */
    boolean cancel(Long gameId);

    /**
     * Make {@link GameDto} state OK.
     *
     * @param gameId id of game to retrieve
     * @return true if game state was changed
     */
    boolean retrieve(Long gameId);

    /**
     * Change game start time.
     *
     * @param gameChangeStartTimeDto
     * @throws IllegalArgumentException if game has been already played (has
     * not null score)
     */
    void changeStartTime(GameChangeStartTimeDto gameChangeStartTimeDto);

    /**
     * Find {@link GameDto} by id.
     *
     * @param gameId of game to find
     * @return GameDto with given id
     */
    GameDto findById(Long gameId);

    /**
     * Find {@link GameDto}s for team.
     *
     * @param teamId id of team playing in game
     * @return list of all gameDtos with given team
     */
    List<GameDto> findByTeam(Long teamId);

    /**
    * Find all {@link GameDto}s.
    *
    * @return list of all games
    */
    List<GameDto> findAll();

    /**
     * Find all gameDtos with state OK which were not played yet (have null score).
     *
     * @return list of all scheduled games
     */
    List<GameDto> findScheduledGames();

    /**
     * Play all not played hockey games in the past
     * and set its score for both teams.
     *
     * @return list of just playd gameDtos (with not null scores)
     */
    List<GameDto> playGames();
}
