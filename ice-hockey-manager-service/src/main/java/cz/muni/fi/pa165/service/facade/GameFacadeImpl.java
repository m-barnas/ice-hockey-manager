package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.enums.GameState;
import cz.muni.fi.pa165.facade.GameFacade;
import cz.muni.fi.pa165.service.GameService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of GameFacade.
 *
 * @author Marketa Elederova
 */
@Service
@Transactional
public class GameFacadeImpl implements GameFacade {

    @Autowired
    private GameService gameService;

    @Autowired
    private TeamService teamService;

    @Autowired
	private BeanMappingService beanMappingService;

    @Override
    public GameDto create(GameCreateDto gameCreateDto) {
        Game game = beanMappingService.mapTo(gameCreateDto, Game.class);
        game.setFirstTeam(teamService.findById(gameCreateDto.getFirstTeamId()));
        game.setSecondTeam(teamService.findById(gameCreateDto.getSecondTeamId()));
        game.setStartTime(gameCreateDto.getStartTime());
        game.setGameState(GameState.OK);
        gameService.create(game);
        return beanMappingService.mapTo(game, GameDto.class);
    }

    @Override
    public void delete(Long gameId) {
        Game game = new Game();
        game.setId(gameId);
        gameService.delete(game);
    }

    @Override
    public boolean cancel(Long gameId) {
        Game game = gameService.findById(gameId);
        if (game.getGameState() == GameState.CANCELED) {
            return false;
        }
        game.setGameState(GameState.CANCELED);
        gameService.update(game);
        return true;
    }

    @Override
    public boolean retrieve(Long gameId) {
        Game game = gameService.findById(gameId);
        if (game.getGameState() == GameState.OK) {
            return false;
        }
        game.setGameState(GameState.OK);
        gameService.update(game);
        return true;
    }

    @Override
    public GameDto changeStartTime(GameChangeStartTimeDto gameChangeStartTimeDto) {
        Game game = gameService.findById(gameChangeStartTimeDto.getId());
        game.setStartTime(gameChangeStartTimeDto.getStartTime());
        Game updatedGame = gameService.update(game);
        return beanMappingService.mapTo(updatedGame, GameDto.class);
    }

    @Override
    public GameDto findById(Long gameId) {
        Game game = gameService.findById(gameId);
        return (game == null) ? null : beanMappingService.mapTo(game, GameDto.class);
    }

    @Override
    public List<GameDto> findByTeam(Long teamId) {
        List<Game> games = gameService.findByTeam(teamService.findById(teamId));
        return beanMappingService.mapTo(games, GameDto.class);
    }

    @Override
    public List<GameDto> findAll() {
        return beanMappingService.mapTo(gameService.findAll(), GameDto.class);
    }

    @Override
    public List<GameDto> findScheduledGames() {
        return beanMappingService.mapTo(gameService.findScheduledGames(), GameDto.class);
    }

    @Override
    public List<GameDto> playGames() {
        return beanMappingService.mapTo(gameService.playGames(), GameDto.class);
    }
}
