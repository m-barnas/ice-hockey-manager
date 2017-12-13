package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.GameDao;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.GameState;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Tests for GameService class.
 *
 * @author Marketa Elederova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private Clock clock;

    @Mock
    private GameDao gameDao;

    @Autowired
    @InjectMocks
    private GameServiceImpl gameService;

    private Team team1;

    private Team team2;

    private Game gameNow;

    private Game gameInFuture;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clock = Clock.fixed(
                LocalDateTime.of(2017, 9, 1, 14, 30).toInstant(OffsetDateTime.now().getOffset()),
                Clock.systemDefaultZone().getZone()
        );
        gameService.setClock(clock);

        Set<HockeyPlayer> players1 = new HashSet<>();
        for (int i = 35; i < 45; ++i) {
            HockeyPlayer hp = new HockeyPlayer();
            hp.setAttackSkill(i);
            hp.setDefenseSkill(i);
            players1.add(hp);
        }
        Set<HockeyPlayer> players2 = new HashSet<>();
        for (int i = 55; i < 65; ++i) {
            HockeyPlayer hp = new HockeyPlayer();
            hp.setAttackSkill(i);
            hp.setDefenseSkill(i);
            players2.add(hp);
        }
        team1 = new Team();
        team1.setName("team1");
        team1.setHockeyPlayers(players1);
        team2 = new Team();
        team2.setName("team2");
        team2.setHockeyPlayers(players2);
    }

    @BeforeMethod
    public void initGames() {
        gameNow = createGame(LocalDateTime.now(clock), false, GameState.OK);
        gameInFuture = createGame(LocalDateTime.of(2017, 10, 5, 5, 5), false, GameState.OK);
    }

    @Test
    public void create() {
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame).isEqualTo(gameNow);
            return null;
        }).when(gameDao).create(Matchers.any(Game.class));

        gameService.create(gameNow);
    }

    @Test
    public void createGameWithBothTeamsSame() {
        gameNow.setSecondTeam(team1);
        assertThatThrownBy(() -> gameService.create(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createGameWithSetedOneScore() {
        gameNow.setSecondTeamScore(1);
        assertThatThrownBy(() -> gameService.create(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void delete() {
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame).isEqualTo(gameNow);
            return null;
        }).when(gameDao).delete(Matchers.any(Game.class));

        gameService.delete(gameNow);
    }

    @Test
    public void update() {
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame).isEqualTo(gameNow);
            return null;
        }).when(gameDao).update(Matchers.any(Game.class));

        gameService.update(gameNow);
    }

    @Test
    public void updateGameWithBothTeamsSame() {
        gameNow.setSecondTeam(team1);
        assertThatThrownBy(() -> gameService.update(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateGameWithSetedOneScore() {
        gameNow.setSecondTeamScore(1);
        assertThatThrownBy(() -> gameService.update(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updatePlayedGame() {
        gameNow.setFirstTeamScore(2);
        gameNow.setSecondTeamScore(1);
        assertThatThrownBy(() -> gameService.update(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findById() {
        when(gameDao.findById(1l)).thenReturn(gameNow);
        Game game = gameService.findById(1l);
        assertThat(game).isEqualTo(gameNow);
    }

    @Test
    public void findByTeam() {
        List<Game> games = new ArrayList<>();
        games.add(gameNow);
        games.add(gameInFuture);
        when(gameDao.findByTeam(team1)).thenReturn(games);

        List<Game> foundGames = gameService.findByTeam(team1);
        assertThat(foundGames).containsExactlyInAnyOrder(gameNow, gameInFuture);
    }

    @Test
    public void findAll() {
        List<Game> games = new ArrayList<>();
        games.add(gameNow);
        games.add(gameInFuture);
        when(gameDao.findAll()).thenReturn(games);

        List<Game> foundGames = gameService.findAll();
        assertThat(foundGames).containsExactlyInAnyOrder(gameNow, gameInFuture);
    }

    @Test
    public void findScheduledGames() {
        List<Game> games = new ArrayList<>();
        games.add(gameNow);
        games.add(gameInFuture);
        when(gameDao.findScheduledGames()).thenReturn(games);

        List<Game> foundGames = gameService.findScheduledGames();
        assertThat(foundGames).containsExactlyInAnyOrder(gameNow, gameInFuture);
    }

    @Test
    public void playGames() {
        List<Game> scheduledGames = new ArrayList<>();
        Game gameInPast = createGame(LocalDateTime.of(2017, 5, 5, 5, 5), false, GameState.OK);
        scheduledGames.add(gameInPast);
        scheduledGames.add(gameInFuture);
        scheduledGames.add(gameNow);
        when(gameDao.findScheduledGames()).thenReturn(scheduledGames);

        List<Game> justPlayedGames = gameService.playGames();
        assertThat(justPlayedGames).containsExactlyInAnyOrder(gameInPast, gameNow);
        assertThat(justPlayedGames.get(0).getFirstTeamScore()).isNotNull();
        assertThat(justPlayedGames.get(0).getSecondTeamScore()).isNotNull();
        assertThat(justPlayedGames.get(1).getFirstTeamScore()).isNotNull();
        assertThat(justPlayedGames.get(1).getSecondTeamScore()).isNotNull();
        assertThat(justPlayedGames.get(0).getFirstTeamScore()).isGreaterThanOrEqualTo(0);
        assertThat(justPlayedGames.get(0).getSecondTeamScore()).isGreaterThanOrEqualTo(0);
        assertThat(justPlayedGames.get(1).getFirstTeamScore()).isGreaterThanOrEqualTo(0);
        assertThat(justPlayedGames.get(1).getSecondTeamScore()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void playGameWithNullGame() {
        Game game = null;
        assertThatThrownBy(() -> gameService.playGame(game)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNullGameState() {
        gameNow.setGameState(null);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNullStartTime() {
        gameNow.setStartTime(null);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNullFirstTeam() {
        gameNow.setFirstTeam(null);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNullSecondTeam() {
        gameNow.setSecondTeam(null);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playCanceledGame() {
        gameNow.setGameState(GameState.CANCELED);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNotNullFirstTeamScore() {
        gameNow.setFirstTeamScore(1);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameWithNotNullSecondTeamScore() {
        gameNow.setSecondTeamScore(1);
        assertThatThrownBy(() -> gameService.playGame(gameNow)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void playGameInFuture() {
        assertThatThrownBy(() -> gameService.playGame(gameInFuture)).isInstanceOf(IllegalArgumentException.class);
    }

    private Game createGame(LocalDateTime startTime, boolean isPlayed, GameState gameState) {
        Game game = new Game();
        game.setFirstTeam(team1);
        game.setSecondTeam(team2);
        game.setStartTime(startTime);
        game.setGameState(gameState);
        if (isPlayed) {
            game.setFirstTeamScore(1);
            game.setSecondTeamScore(2);
        }
        return game;
    }
}
