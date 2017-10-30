package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.repositories.GameRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Implementation of GameDao class.
 *
 * @author Marketa Elederova
 */
@Repository
public class GameDaoImpl implements GameDao {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game findByFirstTeamAndSecondTeamAndStartTime(Team firstTeam, Team secondTeam, LocalDateTime startTime) {
        return gameRepository.findByFirstTeamAndSecondTeamAndStartTime(firstTeam, secondTeam, startTime);
    }

    @Override
    public List<Game> findByTeam(Team team) {
        return gameRepository.findByFirstTeamOrSecondTeam(team, team);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }
}
