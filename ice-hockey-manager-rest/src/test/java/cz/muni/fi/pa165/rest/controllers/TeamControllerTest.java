package cz.muni.fi.pa165.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
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
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Lukas Kotol on 12/16/2017.
 */
@WebAppConfiguration
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TeamControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @InjectMocks
    private TeamController teamController = new TeamController();

    @Mock
    private TeamFacade teamFacade;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(teamController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void getAllTeamsTests() throws Exception {
        doReturn(Collections.unmodifiableList(createTeams()))
                .when(teamFacade)
                .getAllTeams();

        mockMvc.perform(get("http://localhost:8080/pa165/ice-hockey-manager/teams/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Team 1"));

    }

    private List<TeamDto> createTeams() {
        TeamDto teamDto1 = new TeamDto();
        teamDto1.setId(1L);
        teamDto1.setName("Team 1");
        teamDto1.setBudget(BigDecimal.valueOf(100));
        teamDto1.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        teamDto1.setHumanPlayerId(1L);
        teamDto1.setHockeyPlayers(createHockeyPlayers());

        TeamDto teamDto2 = new TeamDto();
        teamDto2.setId(2L);
        teamDto2.setName("Team 2");
        teamDto2.setBudget(BigDecimal.valueOf(500));
        teamDto2.setCompetitionCountry(CompetitionCountry.ENGLAND);
        teamDto2.setHumanPlayerId(1L);

        return Arrays.asList(teamDto1, teamDto2);

    }


    private Set<HockeyPlayerDto> createHockeyPlayers() {
        HockeyPlayerDto hockeyPlayerDto = new HockeyPlayerDto();
        hockeyPlayerDto.setId(1L);
        hockeyPlayerDto.setName("Jaromir Jagr");
        hockeyPlayerDto.setAttackSkill(100);
        hockeyPlayerDto.setDefenseSkill(50);
        hockeyPlayerDto.setPrice(BigDecimal.valueOf(500));
        hockeyPlayerDto.setPost(Position.RIGHT_WING);
        return new HashSet<>(Collections.singletonList(hockeyPlayerDto));
    }
}
