package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.enums.GameState;
import cz.muni.fi.pa165.facade.GameFacade;
import static cz.muni.fi.pa165.rest.ApiUri.ROOT_URI_GAMES;
import cz.muni.fi.pa165.rest.config.RestConfiguration;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for GameController.
 *
 * @author Marketa Elederova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GameFacade gameFacade;

    @InjectMocks
    private final GameController gameController = new GameController();

    private MockMvc mockMvc;

    //@Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Mock
    private Clock clock;

    GameDto gameDto;
    GameDto gameDto2;

    @BeforeClass
    public void setUp() {
        RestConfiguration restConfiguration = new RestConfiguration();//?
        mappingJackson2HttpMessageConverter = restConfiguration.customJackson2HttpMessageConverter();
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(gameController).setMessageConverters(mappingJackson2HttpMessageConverter).build();
        clock = Clock.fixed(
                LocalDateTime.of(2017, 9, 1, 14, 30).toInstant(OffsetDateTime.now().getOffset()),
                ZoneId.of("Europe/Paris")
        );
    }

    @BeforeMethod
    public void initGames() {
        TeamDto teamDto1 = new TeamDto();
        teamDto1.setName("team1");
        TeamDto teamDto2 = new TeamDto();
        teamDto2.setName("team2");

        gameDto = new GameDto();
        gameDto.setId(1l);
        gameDto.setFirstTeamDto(teamDto1);
        gameDto.setSecondTeamDto(teamDto2);
        gameDto.setStartTime(LocalDateTime.now(clock));
        gameDto.setGameState(GameState.OK);

        gameDto2 = new GameDto();
        gameDto2.setId(2l);
        gameDto2.setFirstTeamDto(teamDto1);
        gameDto2.setSecondTeamDto(teamDto2);
        gameDto2.setStartTime(LocalDateTime.of(2016, Month.MARCH, 2, 10, 0));
    }

    @Test
    public void createGame() throws Exception {
        GameCreateDto gameCreateDto = new GameCreateDto();
        gameCreateDto.setFirstTeamId(1l);
        gameCreateDto.setSecondTeamId(2l);
//                gameCreateDto.setStartTime(LocalDateTime.now(clock));

        when(gameFacade.create(gameCreateDto)).thenReturn(gameDto);
        String GameCreateJson = convertObjectToJson(gameCreateDto);
//        System.out.println(GameCreateJson);

        MvcResult result = mockMvc.perform(post(ROOT_URI_GAMES + "/create")
                .contentType(MediaType.APPLICATION_JSON).content(GameCreateJson))
                .andExpect(status().isOk()).andReturn();
        String gameResultJson = result.getResponse().getContentAsString();
//        System.out.println(gameResultJson);
        assertThat(gameResultJson).isEqualTo(convertObjectToJson(gameDto));
    }

    @Test
    public void createInvalidGame() throws Exception {
        GameCreateDto gameCreateDto = new GameCreateDto();
        doThrow(new IllegalArgumentException()).when(gameFacade).create(any(GameCreateDto.class));
        String GameCreateJson = convertObjectToJson(gameCreateDto);

        mockMvc.perform(post(ROOT_URI_GAMES + "/create")
                .contentType(MediaType.APPLICATION_JSON).content(GameCreateJson))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteGame() throws Exception {
        doNothing().when(gameFacade).delete(1l);

        mockMvc.perform(delete(ROOT_URI_GAMES + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingGame() throws Exception {
        doThrow(new InvalidDataAccessApiUsageException("attempt to create merge event with null entity")).when(gameFacade).delete(any(Long.class));

        mockMvc.perform(delete(ROOT_URI_GAMES + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void cancelGame() throws Exception {
        doReturn(true).when(gameFacade).cancel(1l);

        mockMvc.perform(put(ROOT_URI_GAMES + "/cancel/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelNotExistingGame() throws Exception {
        doThrow(new IllegalArgumentException("Game with id 1 not found")).when(gameFacade).cancel(1l);

        mockMvc.perform(put(ROOT_URI_GAMES + "/cancel/1"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void cancelPlayedGame() throws Exception {
        doThrow(new IllegalArgumentException("Invalid game")).when(gameFacade).cancel(1l);

        mockMvc.perform(put(ROOT_URI_GAMES + "/cancel/1"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void retrieveGame() throws Exception {
        doReturn(true).when(gameFacade).retrieve(1l);

        mockMvc.perform(put(ROOT_URI_GAMES + "/retrieve/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void retrieveNotExistingGame() throws Exception {
        doThrow(new IllegalArgumentException("Game with id 1 not found")).when(gameFacade).retrieve(any(Long.class));

        mockMvc.perform(put(ROOT_URI_GAMES + "/retrieve/1"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void changeStartTime() throws Exception {
        GameChangeStartTimeDto gameChangeStartTimeDto = new GameChangeStartTimeDto();
        gameChangeStartTimeDto.setId(1l);
//        gameChangeStartTimeDto.setStartTime(LocalDateTime.now(clock));

        when(gameFacade.changeStartTime(gameChangeStartTimeDto)).thenReturn(gameDto);

        String GameChangeStartTimeJson = convertObjectToJson(gameChangeStartTimeDto);
//        System.out.println(GameChangeStartTimeJson);

        MvcResult result = mockMvc.perform(put(ROOT_URI_GAMES + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(GameChangeStartTimeJson))
                .andExpect(status().isOk()).andReturn();
        String gameResultJson = result.getResponse().getContentAsString();
//        System.out.println(gameResultJson);
        assertThat(gameResultJson).isEqualTo(convertObjectToJson(gameDto));
    }

    @Test
    public void findById() throws Exception {
        doReturn(gameDto).when(gameFacade).findById(1l);

        MvcResult result = mockMvc.perform(get(ROOT_URI_GAMES + "/1"))
                .andExpect(status().isOk()).andReturn();
        String gameResultJson = result.getResponse().getContentAsString();
        assertThat(gameResultJson).isEqualTo(convertObjectToJson(gameDto));
    }

    @Test
    public void findNotExistingGameById() throws Exception {
        doReturn(null).when(gameFacade).findById(1l);

        mockMvc.perform(get(ROOT_URI_GAMES + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByTeam() throws Exception {
        List<GameDto> games = new ArrayList();
        games.add(gameDto);
        games.add(gameDto2);
        doReturn(games).when(gameFacade).findByTeam(1l);

        MvcResult result = mockMvc.perform(get(ROOT_URI_GAMES + "/byteam?teamId=1"))
                .andExpect(status().isOk()).andReturn();
        String gamesResultJson = result.getResponse().getContentAsString();
        assertThat(gamesResultJson).isEqualTo(convertObjectToJson(games));
    }

    @Test
    public void findByNotExistingTeam() throws Exception {
        doThrow(new IllegalArgumentException("Team with id 1 not found")).when(gameFacade).findByTeam(1l);

        mockMvc.perform(get(ROOT_URI_GAMES + "/byteam?teamId=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAll() throws Exception {
        List<GameDto> games = new ArrayList();
        games.add(gameDto);
        games.add(gameDto2);
        doReturn(games).when(gameFacade).findAll();

        MvcResult result = mockMvc.perform(get(ROOT_URI_GAMES + "/all"))
                .andExpect(status().isOk()).andReturn();
        String gamesResultJson = result.getResponse().getContentAsString();
        assertThat(gamesResultJson).isEqualTo(convertObjectToJson(games));
    }

    @Test
    public void findScheduledGames() throws Exception {
        List<GameDto> games = new ArrayList();
        games.add(gameDto);
        games.add(gameDto2);
        doReturn(games).when(gameFacade).findScheduledGames();

        MvcResult result = mockMvc.perform(get(ROOT_URI_GAMES + "/scheduled"))
                .andExpect(status().isOk()).andReturn();
        String gamesResultJson = result.getResponse().getContentAsString();
        assertThat(gamesResultJson).isEqualTo(convertObjectToJson(games));
    }

    @Test
    public void playGames() throws Exception {
        gameDto.setFirstTeamScore(2);
        gameDto.setSecondTeamScore(1);
        gameDto2.setFirstTeamScore(1);
        gameDto2.setSecondTeamScore(0);
        List<GameDto> games = new ArrayList();
        games.add(gameDto);
        games.add(gameDto2);
        doReturn(games).when(gameFacade).playGames();

        MvcResult result = mockMvc.perform(put(ROOT_URI_GAMES + "/play"))
                .andExpect(status().isOk()).andReturn();
        String gamesResultJson = result.getResponse().getContentAsString();
        System.out.println(gamesResultJson);
        assertThat(gamesResultJson).isEqualTo("2");
    }

    private String convertObjectToJson(Object object) throws Exception {
        ObjectMapper mapper = mappingJackson2HttpMessageConverter.getObjectMapper();
//            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
