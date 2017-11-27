package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.GameState;
import cz.muni.fi.pa165.service.GameService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.GameFacadeImpl;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for GameFacadeImpl.
 *
 * @author Marketa Elederova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GameFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GameService gameService;

    @Mock
    private TeamService teamService;

    @Mock
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private GameFacadeImpl gameFacade;

    private Team team1;

    private Team team2;

    private Game game;

    @Mock
    private Clock clock;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clock = Clock.fixed(
                LocalDateTime.of(2017, 9, 1, 14, 30).toInstant(OffsetDateTime.now().getOffset()),
                Clock.systemDefaultZone().getZone()
        );
    }

    @BeforeMethod
    public void initGames() {
        team1 = new Team();
        team1.setId(1l);
        team1.setName("team1");
        team2 = new Team();
        team2.setId(2l);
        team2.setName("team2");
        game = new Game();
        game.setId(1l);
        game.setFirstTeam(team1);
        game.setSecondTeam(team2);
        game.setStartTime(LocalDateTime.now(clock));
        game.setGameState(GameState.OK);
    }

    @Test
    public void create() {
        when(teamService.findById(1l)).thenReturn(team1);
        when(teamService.findById(2l)).thenReturn(team2);
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame.getFirstTeam()).isEqualTo(team1);
            assertThat(createdGame.getSecondTeam()).isEqualTo(team2);
            assertThat(createdGame.getStartTime()).isEqualTo(LocalDateTime.now(clock));
            assertThat(createdGame.getGameState()).isEqualTo(GameState.OK);
            return null;
        }).when(gameService).create(Matchers.any(Game.class));

        GameCreateDto gameCreateDto = new GameCreateDto();
        gameCreateDto.setFirstTeamId(1l);
        gameCreateDto.setSecondTeamId(2l);
        gameCreateDto.setStartTime(LocalDateTime.now(clock));
        gameFacade.create(gameCreateDto);
    }

    @Test
    public void cancel() {
        when(gameService.findById(1l)).thenReturn(game);
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame.getGameState()).isEqualTo(GameState.CANCELED);
            return null;
        }).when(gameService).update(Matchers.any(Game.class));

        assertThat(gameFacade.cancel(1l)).isTrue();
        assertThat(gameFacade.cancel(1l)).isFalse();
    }

    @Test
    public void retrieve() {
        when(gameService.findById(1l)).thenReturn(game);
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame.getGameState()).isEqualTo(GameState.OK);
            return null;
        }).when(gameService).update(Matchers.any(Game.class));

        game.setGameState(GameState.CANCELED);
        assertThat(gameFacade.retrieve(1l)).isTrue();
        assertThat(gameFacade.retrieve(1l)).isFalse();
    }

    @Test
    public void changeStartTime() {
        when(gameService.findById(1l)).thenReturn(game);
        doAnswer((Answer<Object>) (InvocationOnMock invocationOnMock) -> {
            Object[] args = invocationOnMock.getArguments();
            Game createdGame = (Game) args[0];
            assertThat(createdGame.getStartTime()).isEqualTo(LocalDateTime.of(2017, 2, 2, 0, 0));
            return null;
        }).when(gameService).update(Matchers.any(Game.class));

        GameChangeStartTimeDto gameChangeStartTimeDto = new GameChangeStartTimeDto();
        gameChangeStartTimeDto.setId(1l);
        gameChangeStartTimeDto.setStartTime(LocalDateTime.of(2017, 2, 2, 0, 0));
        gameFacade.changeStartTime(gameChangeStartTimeDto);
    }

    @Test
    public void findById() {
        when(gameService.findById(1l)).thenReturn(game);
        GameDto gameDto = gameFacade.findById(1l);
        compareGameDtoToGame(gameDto);
    }

    @Test
    public void findByTeam() {
        when(teamService.findById(1l)).thenReturn(team1);
        List<Game> games = new ArrayList<>();
        games.add(game);
        when(gameService.findByTeam(team1)).thenReturn(games);

        List<GameDto> gameDtos = gameFacade.findByTeam(1l);
        assertThat(gameDtos.size()).isEqualTo(1);
        GameDto gameDto = gameDtos.get(0);
        compareGameDtoToGame(gameDto);
    }

    @Test
    public void findAll() {
        List<Game> games = new ArrayList<>();
        games.add(game);
        when(gameService.findAll()).thenReturn(games);
        List<GameDto> gameDtos = gameFacade.findAll();
        assertThat(gameDtos.size()).isEqualTo(1);
        GameDto gameDto = gameDtos.get(0);
        compareGameDtoToGame(gameDto);
    }

    @Test
    public void findScheduledGames() {
        List<Game> games = new ArrayList<>();
        games.add(game);
        when(gameService.findScheduledGames()).thenReturn(games);
        List<GameDto> gameDtos = gameFacade.findScheduledGames();
        assertThat(gameDtos.size()).isEqualTo(1);
        GameDto gameDto = gameDtos.get(0);
        compareGameDtoToGame(gameDto);
    }

    @Test
    public void playGames() {
        List<Game> games = new ArrayList<>();
        games.add(game);
        when(gameService.playGames()).thenReturn(games);
        List<GameDto> gameDtos = gameFacade.playGames();
        assertThat(gameDtos.size()).isEqualTo(1);
        GameDto gameDto = gameDtos.get(0);
        compareGameDtoToGame(gameDto);
    }

    private void compareGameDtoToGame(GameDto gameDto) {
        assertThat(gameDto.getId()).isEqualTo(1l);
        assertThat(gameDto.getFirstTeamDto().getName()).isEqualTo("team1");
        assertThat(gameDto.getSecondTeamDto().getName()).isEqualTo("team2");
        assertThat(gameDto.getStartTime()).isEqualTo(LocalDateTime.now(clock));
        assertThat(gameDto.getGameState()).isEqualTo(GameState.OK);
        assertThat(gameDto.getFirstTeamScore()).isNull();
        assertThat(gameDto.getSecondTeamScore()).isNull();
    }
}
