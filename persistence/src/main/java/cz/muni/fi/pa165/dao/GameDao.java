package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Marketa Elederova
 */
public interface GameDao {

    /**
    * Update {@link Game}.
    *
    * @param game to save
    * @return saved game
    */
    Game save(Game game);

    /**
    * Delete {@link Game}.
    *
    * @param game to delete
    */
    void delete(Game game);

    /**
    * Find {@link Game} by id.
    *
    * @param id to find
    * @return game with given id
    */
    Game findById(Long id);

    /**
     * Find {@link Game}s by first team, second team and start time.
     *
     * @param firstTeam
     * @param secondTeam
     * @param startTime
     * @return all games with given first team, second team and start time
     */
    Game findByFirstTeamAndSecondTeamAndStartTime(Team firstTeam, Team secondTeam, LocalDateTime startTime);

    /**
     * Find {@link Game}s by any team.
     *
     * @param team
     * @return all games of given team
     */
    List<Game> findByTeam(Team team);

    /**
    * Find all {@link Game}s.
    *
    * @return all games
    */
    List<Game> findAll();
}
