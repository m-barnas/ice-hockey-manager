package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.GameState;
import cz.muni.fi.pa165.service.GameService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
public class GameFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GameService gameService;

    @Mock
    private TeamService teamService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private GameFacadeImpl gameFacade = new GameFacadeImpl();

    private Team team1;
    private Team team2;
    private Game game;
    private TeamDto teamDto1;
    private TeamDto teamDto2;
    private GameDto gameDto;
    private List<GameDto> gameDtos;
    private List<Game> games;


    @Mock
    private Clock clock;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clock = Clock.fixed(
                LocalDateTime.of(2017, 9, 1, 14, 30).toInstant(OffsetDateTime.now().getOffset()),
                ZoneId.of("Europe/Paris")
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
        teamDto1 = new TeamDto();
        teamDto1.setName("team1");
        teamDto2 = new TeamDto();
        teamDto2.setName("team2");
        gameDto = new GameDto();
        gameDto.setId(1l);
        gameDto.setFirstTeamDto(teamDto1);
        gameDto.setSecondTeamDto(teamDto2);
        gameDto.setStartTime(LocalDateTime.now(clock));
        gameDto.setGameState(GameState.OK);
        gameDtos = new ArrayList<>();
        gameDtos.add(gameDto);
        games = new ArrayList<>();
        games.add(game);

        when(beanMappingService.mapToGame(games, GameDto.class)).thenReturn(gameDtos);
        when(beanMappingService.mapToGame(game, GameDto.class)).thenReturn(gameDto);
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
            createdGame.setId(1l);
            return null;
        }).when(gameService).create(Matchers.any(Game.class));
        GameCreateDto gameCreateDto = new GameCreateDto();
        gameCreateDto.setFirstTeamId(1l);
        gameCreateDto.setSecondTeamId(2l);
        gameCreateDto.setStartTime(LocalDateTime.now(clock));
        Game g = new Game();
        g.setFirstTeam(team1);
        g.setSecondTeam(team2);
        g.setStartTime(LocalDateTime.now(clock));
        when(beanMappingService.mapTo(gameCreateDto, Game.class)).thenReturn(g);
        when(beanMappingService.mapToGame(game, GameDto.class)).thenReturn(gameDto);

        assertThat(gameFacade.create(gameCreateDto)).isEqualTo(gameDto);
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
        GameDto foundGameDto = gameFacade.findById(1l);
        compareGameDtoToGame(foundGameDto);
    }

    @Test
    public void findByTeam() {
        when(teamService.findById(1l)).thenReturn(team1);
        when(gameService.findByTeam(team1)).thenReturn(games);

        List<GameDto> foundGameDtos = gameFacade.findByTeam(1l);
        assertThat(foundGameDtos.size()).isEqualTo(1);
        GameDto foundGameDto = foundGameDtos.get(0);
        compareGameDtoToGame(foundGameDto);
    }

    @Test
    public void findAll() {
        when(gameService.findAll()).thenReturn(games);
        List<GameDto> foundGameDtos = gameFacade.findAll();
        assertThat(foundGameDtos.size()).isEqualTo(1);
        GameDto foundGameDto = foundGameDtos.get(0);
        compareGameDtoToGame(foundGameDto);
    }

    @Test
    public void findScheduledGames() {
        when(gameService.findScheduledGames()).thenReturn(games);
        List<GameDto> foundGameDtos = gameFacade.findScheduledGames();
        assertThat(foundGameDtos.size()).isEqualTo(1);
        GameDto foundGameDto = foundGameDtos.get(0);
        compareGameDtoToGame(foundGameDto);
    }

    @Test
    public void playGames() {
        when(gameService.playGames()).thenReturn(games);
        List<GameDto> foundGameDtos = gameFacade.playGames();
        assertThat(foundGameDtos.size()).isEqualTo(1);
        GameDto foundGameDto = foundGameDtos.get(0);
        compareGameDtoToGame(foundGameDto);
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
