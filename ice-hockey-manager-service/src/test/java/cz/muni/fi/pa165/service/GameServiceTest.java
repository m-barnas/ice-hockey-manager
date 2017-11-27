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
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

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
        team1.setHockeyPlayers(players1);
        team2 = new Team();
        team2.setHockeyPlayers(players2);
    }

    @Test
    public void findScheduledGames() {
        List<Game> scheduledGames = new ArrayList<>();
        Game gameInPast = createGame(LocalDateTime.of(2017, 5, 5, 5, 5), false, GameState.OK);
        Game gameInFuture = createGame(LocalDateTime.of(2017, 10, 5, 5, 5), false, GameState.OK);
        scheduledGames.add(gameInPast);
        scheduledGames.add(gameInFuture);
        when(gameDao.findScheduledGames()).thenReturn(scheduledGames);

        assertThat(gameService.findScheduledGames()).containsExactlyInAnyOrder(gameInPast, gameInFuture);
    }

    @Test
    public void playGames() {
        List<Game> scheduledGames = new ArrayList<>();
        Game gameInPast = createGame(LocalDateTime.of(2017, 5, 5, 5, 5), false, GameState.OK);
        Game gameInFuture = createGame(LocalDateTime.of(2017, 10, 5, 5, 5), false, GameState.OK);
        Game gameNow = createGame(LocalDateTime.now(clock), false, GameState.OK);
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
