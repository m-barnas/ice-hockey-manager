package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.dto.TeamCreateDTO;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.TeamFacade;
import cz.muni.fi.pa165.service.facade.TeamFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Lukas Kotol on 11/25/2017.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TeamFacadeTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TeamFacade teamFacade;

    private TeamCreateDTO teamCreateDTO;

    private TeamDTO teamDTO;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestTeam() {
        teamCreateDTO = new TeamCreateDTO();
        teamCreateDTO.setName("teamTestCreate");
        teamCreateDTO.setBudget(BigDecimal.valueOf(3000));
        teamCreateDTO.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        teamCreateDTO.setHumanPlayerId(1L);
        teamDTO = new TeamDTO();
        teamDTO.setName("teamTest");
    }

    @Test
    public void createTeamTest() {
        Long teamId = teamFacade.createTeam(teamCreateDTO);
        assertThat(teamFacade.getTeamById(teamId).getName()).isEqualTo(teamCreateDTO.getName());
        assertThat(teamFacade.getTeamById(teamId).getBudget()).isEqualTo(teamCreateDTO.getBudget());
        assertThat(teamFacade.getTeamById(teamId).getCompetitionCountry()).isEqualTo(teamCreateDTO.getCompetitionCountry());
    }

    @Test
    public void deleteTeamTest() {
        Long teamId = teamFacade.createTeam(teamCreateDTO);
        teamFacade.deleteTeam(teamId);
        assertThat(teamFacade.getTeamById(teamId)).isEqualTo(null);
    }

    @Test
    public void findAllTeamTeast(){
        Long teamId = teamFacade.createTeam(teamCreateDTO);
        List<TeamDTO> teams = teamFacade.getAllTeams();
        TeamDTO teamDTO = teamFacade.getTeamById(teamId);
        assertThat(teams).containsExactly(teamDTO);
    }

    @Test
    public void findByCompetitionCountryTeamTest(){
        Long teamId = teamFacade.createTeam(teamCreateDTO);
        List<TeamDTO> teams = teamFacade.getTeamsByCountry(CompetitionCountry.CZECH_REPUBLIC);
        TeamDTO teamDTO = teamFacade.getTeamById(teamId);
        assertThat(teams).containsExactly(teamDTO);
    }

    @Test
    public void addHockeyPlayerTeamTest(){
        Long teamId = teamFacade.createTeam(teamCreateDTO);
//        teamFacade.addHockyPlayer(teamId, hockeyPlayerId);
//        assertThat(teamFacade.getTeamById(teamId).getHockeyPlayers()).contains(hockeyPlayer)  ;
    }

    @Test
    public void removeHockeyPlayerTeamTest(){
        Long teamId = teamFacade.createTeam(teamCreateDTO);
//        teamFacade.addHockyPlayer(teamId, hockeyPlayerId);
//        assertThat(teamFacade.getTeamById(teamId).getHockeyPlayers()) contains(hockeyPlayer);
    }

}
