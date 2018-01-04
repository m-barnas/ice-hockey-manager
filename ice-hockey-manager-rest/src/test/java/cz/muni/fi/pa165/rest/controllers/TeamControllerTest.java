package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamAddRemovePlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.dto.TeamSpendMoneyDto;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.rest.ApiUri;
import cz.muni.fi.pa165.rest.config.RestConfiguration;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.junit.Ignore;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.*;

import static cz.muni.fi.pa165.rest.ApiUri.ROOT_URI_TEAMS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Lukas Kotol on 12/16/2017.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceConfiguration.class, RestConfiguration.class})
public class TeamControllerTest extends AbstractTestNGSpringContextTests {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private TeamController teamController = new TeamController();
//
//    @Mock
//    private TeamFacade teamFacade;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
//
//    @BeforeClass
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = standaloneSetup(teamController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
//    }
//
//    @Test
//    public void getAllTeamsTests() throws Exception {
//        doReturn(Collections.unmodifiableList(createTeams()))
//                .when(teamFacade)
//                .getAllTeams();
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Team 1"))
//                .andExpect(jsonPath("$.[?(@.id==1)].budget").value(100))
//                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Team 2"));
//    }
//
//    @Test
//    public void findByIdTest() throws Exception {
//        doReturn(createTeams().get(0))
//                .when(teamFacade)
//                .getTeamById(1L);
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.name").value("Team 1"))
//                .andExpect(jsonPath("$.budget").value(100))
//                .andExpect(jsonPath("$.competitionCountry").value("CZECH_REPUBLIC"));
//    }
//
//    @Test
//    public void getByNameTest() throws Exception {
//        doReturn(createTeams().get(0))
//                .when(teamFacade)
//                .findTeamByName("Team 1");
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/getByName/Team 1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.name").value("Team 1"))
//                .andExpect(jsonPath("$.budget").value(100))
//                .andExpect(jsonPath("$.competitionCountry").value("CZECH_REPUBLIC"));
//    }
//
//    @Test
//    public void getByCompetitionCountryTest() throws Exception {
//        doReturn(Collections.unmodifiableList(createTeams().subList(0, 1)))
//                .when(teamFacade)
//                .getTeamsByCountry(CompetitionCountry.CZECH_REPUBLIC);
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/getByCompetitionCountry/CZECH_REPUBLIC"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Team 1"))
//                .andExpect(jsonPath("$.[?(@.id==1)].budget").value(100))
//                .andExpect(jsonPath("$.[?(@.id==1)].competitionCountry").value("CZECH_REPUBLIC"));
//    }
//
//    @Test
//    public void spendMoneyFromBudgetTest() throws Exception {
//        doNothing().when(teamFacade).spendMoneyFromBudget(1L, BigDecimal.valueOf(20));
//        TeamDto teamWithLessBudget = createTeams().get(0);
//        teamWithLessBudget.setBudget(BigDecimal.valueOf(80));
//        doReturn(teamWithLessBudget).when(teamFacade).getTeamById(1L);
//
//        TeamSpendMoneyDto teamSpendMoneyDto = new TeamSpendMoneyDto();
//        teamSpendMoneyDto.setTeamId(1L);
//        teamSpendMoneyDto.setAmount(BigDecimal.valueOf(20));
//
//        mockMvc.perform(post(ROOT_URI_TEAMS + "/spendMoneyFromBudget")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJson(teamSpendMoneyDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Team 1"))
//                .andExpect(jsonPath("$.budget").value(80));
//    }
//
//    @Test
//    public void getPriceTest() throws Exception {
//        doReturn(BigDecimal.valueOf(500)).when(teamFacade).getTeamPrice(createTeams().get(0).getId());
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/1/price"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$").value(500));
//    }
//
//    @Test
//    public void getAttackTest() throws Exception {
//        doReturn(100).when(teamFacade).getTeamAttackSkill(createTeams().get(0).getId());
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/1/attack")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(100));
//    }
//
//    @Test
//    public void getDefenseTest() throws Exception {
//        doReturn(50).when(teamFacade).getTeamDefenseSkill(createTeams().get(0).getId());
//
//        mockMvc.perform(get(ROOT_URI_TEAMS + "/1/defense"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$").value(50));
//    }
//
//    @Test
//    public void createTeamTest() throws Exception {
//        List<TeamDto> teamDtos = createTeams();
//        doReturn(1L).when(teamFacade).createTeam(teamDtos.get(0));
//        mockMvc.perform(put(ROOT_URI_TEAMS + ApiUri.SubApiUri.CREATE)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJson(teamDtos.get(0))))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(jsonPath("$.name").value("Team 1"))
//                .andExpect(jsonPath("$.budget").value(100))
//                .andExpect(jsonPath("$.competitionCountry").value("CZECH_REPUBLIC"));
//    }
//
//    @Test
//    public void addHockeyPlayerTest() throws Exception {
//        doNothing().when(teamFacade).addHockeyPlayer(Mockito.anyLong(), Mockito.anyLong());
//        doReturn(createTeams().get(0)).when(teamFacade).getTeamById(1L);
//
//        TeamAddRemovePlayerDto teamAddRemovePlayerDto = new TeamAddRemovePlayerDto();
//        teamAddRemovePlayerDto.setTeamId(1L);
//        teamAddRemovePlayerDto.setHockeyPlayerId(1L);
//
//        mockMvc.perform(post(ROOT_URI_TEAMS + "/addHockeyPlayer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJson(teamAddRemovePlayerDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
//    }
//
//    @Test
//    public void removeHockeyPlayerTest() throws Exception {
//        doNothing().when(teamFacade).removeHockeyPlayer(Mockito.anyLong(), Mockito.anyLong());
//        doReturn(createTeams().get(0)).when(teamFacade).getTeamById(1L);
//
//        TeamAddRemovePlayerDto teamAddRemovePlayerDto = new TeamAddRemovePlayerDto();
//        teamAddRemovePlayerDto.setTeamId(1L);
//        teamAddRemovePlayerDto.setHockeyPlayerId(1L);
//
//        mockMvc.perform(post(ROOT_URI_TEAMS + "/removeHockeyPlayer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(convertObjectToJson(teamAddRemovePlayerDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
//    }
//
//    private List<TeamDto> createTeams() {
//        TeamDto teamDto1 = new TeamDto();
//        teamDto1.setId(1L);
//        teamDto1.setName("Team 1");
//        teamDto1.setBudget(BigDecimal.valueOf(100));
//        teamDto1.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
//        teamDto1.setHumanPlayerId(1L);
//        teamDto1.setHockeyPlayers(createHockeyPlayers());
//
//        TeamDto teamDto2 = new TeamDto();
//        teamDto2.setId(2L);
//        teamDto2.setName("Team 2");
//        teamDto2.setBudget(BigDecimal.valueOf(500));
//        teamDto2.setCompetitionCountry(CompetitionCountry.ENGLAND);
//        teamDto2.setHumanPlayerId(1L);
//
//        return Arrays.asList(teamDto1, teamDto2);
//
//    }
//
//
//    private Set<HockeyPlayerDto> createHockeyPlayers() {
//        HockeyPlayerDto hockeyPlayerDto = new HockeyPlayerDto();
//        hockeyPlayerDto.setId(1L);
//        hockeyPlayerDto.setName("Jaromir Jagr");
//        hockeyPlayerDto.setAttackSkill(100);
//        hockeyPlayerDto.setDefenseSkill(50);
//        hockeyPlayerDto.setPrice(BigDecimal.valueOf(500));
//        hockeyPlayerDto.setPost(Position.RIGHT_WING);
//        return new HashSet<>(Collections.singletonList(hockeyPlayerDto));
//    }
//
//    private String convertObjectToJson(Object object) throws Exception {
//        ObjectMapper mapper = mappingJackson2HttpMessageConverter.getObjectMapper();
//        return mapper.writeValueAsString(object);
//    }
}
