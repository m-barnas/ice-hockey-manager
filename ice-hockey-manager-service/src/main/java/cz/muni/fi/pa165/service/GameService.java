package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;

import java.util.List;

/**
 * Class for managing games.
 *
 * @author Marketa Elederova
 */
public interface GameService {

    /**
     * Create new {@link Game}.
     *
     * @param game to create
     */
    void create(Game game);

    /**
     * Delete {@link Game} from system.
     *
     * @param game to delete
     */
    void delete(Game game);

    /**
     * Update {@link Game}.
     *
     * @param game to update (game can not be already played)
     * @return updated game
     */
    Game update(Game game);

    /**
     * Find {@link Game} by id.
     *
     * @param id game id
     * @return Game with given id
     */
    Game findById(Long id);

    /**
     * Find {@link Game}s of team.
     *
     * @param team playing in game
     * @return list of all games of given team
     */
    List<Game> findByTeam(Team team);

    /**
    * Find all {@link Game}s.
    *
    * @return list of all games
    */
    List<Game> findAll();

    /**
     * Find all games with state OK which were not played yet (have null score).
     *
     * @return list of all scheduled games
     */
    List<Game> findScheduledGames();

    /**
     * Play all not played hockey games in the past
     * and set its score for both teams.
     *
     * @return list of just playd games (with not null scores)
     */
    List<Game> playGames();
}
