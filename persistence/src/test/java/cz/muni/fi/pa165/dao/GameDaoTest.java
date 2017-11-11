package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GameDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private TeamDao teamDao;

    private Game gameOk;

    private Game gameCanceled;

    @BeforeMethod
    public void init() {
        createGames();
    }

    @Test
    public void createGameOk() {
        gameDao.create(gameOk);

        Game game = gameDao.findById(gameOk.getId());

        assertThat(game).isEqualTo(gameOk);
    }

    @Test
    public void createMissingFirstTeam() {
        gameOk.setFirstTeam(null);

        assertThatThrownBy(() -> gameDao.create(gameOk)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createMissingSecondTeam() {
        gameOk.setSecondTeam(null);

        assertThatThrownBy(() -> gameDao.create(gameOk)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createMissingStartTime() {
        gameOk.setStartTime(null);

        assertThatThrownBy(() -> gameDao.create(gameOk)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void update() {
        gameDao.create(gameOk);

        Game game = gameDao.findById(gameOk.getId());
        game.setGameState(GameState.CANCELED);
        Game updatedGame = gameDao.update(game);

        assertThat(gameDao.findById(updatedGame.getId()).getGameState()).isEqualTo(GameState.CANCELED);
    }

    @Test
    public void delete() {
        gameDao.create(gameOk);
        gameDao.create(gameCanceled);
        gameDao.delete(gameOk);

        assertThat(gameDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void findById() {
        gameDao.create(gameOk);
        gameDao.create(gameCanceled);

        Game game = gameDao.findById(gameOk.getId());
        assertThat(game).isEqualTo(gameOk);
    }

    @Test
    public void findByFirstTeamAndSecondTeamAndStartTime() {
        gameDao.create(gameOk);

        Game game = gameDao.findByFirstTeamAndSecondTeamAndStartTime(gameOk.getFirstTeam(),
                gameOk.getSecondTeam(), gameOk.getStartTime());

        assertThat(game).isEqualTo(gameOk);
    }

    @Test
    public void findByTeam() {
        gameDao.create(gameCanceled);

        List<Game> games = gameDao.findByTeam(gameCanceled.getSecondTeam());

        assertThat(games).contains(gameCanceled);
    }

    @Test
    public void findAll() {
        gameDao.create(gameOk);
        gameDao.create(gameCanceled);

        assertThat(gameDao.findAll()).hasSize(2);
    }

    private void createGames() {
        gameOk = createGame(GameState.OK, "First Team", "Second Team");
        gameCanceled = createGame(GameState.CANCELED, "Third Team", "Fourth Team");
    }

    private Game createGame(GameState gameState, String firstTeam, String secondTeam) {
        Game game = new Game();
        game.setGameState(gameState);
        game.setFirstTeam(createTeam(firstTeam));
        game.setSecondTeam(createTeam(secondTeam));
        game.setStartTime(LocalDateTime.now());
        return game;
    }

    private Team createTeam( String name) {
        Team team = new Team();
        team.setName(name);
        team.setCompetitionCountry(CompetitionCountry.ENGLAND);
        team.setBudget(new BigDecimal("0"));
        teamDao.save(team);
        return team;
    }
}
