package cz.muni.fi.pa165.repositories;

import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Marketa Elederova
 */
public interface GameRepository extends CrudRepository<Game, Long> {

    Game findById(Long id);

    Game findByFirstTeamAndSecondTeamAndStartTime(Team firstTeam, Team secondTeam, LocalDateTime startTime);

    List<Game> findByFirstTeamOrSecondTeam(Team firstTeam, Team secondTeam);

    List<Game> findByFirstTeam(Team firstTeam);

    List<Game> findBySecondTeam(Team secondTeam);

    List<Game> findByStartTime(LocalDateTime startTime);

    List<Game> findAll();
}
