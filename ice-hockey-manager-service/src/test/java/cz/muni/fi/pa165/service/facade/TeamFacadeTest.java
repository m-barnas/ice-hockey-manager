package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.TeamCreateDTO;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Lukas Kotol on 11/25/2017.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TeamFacadeTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private TeamFacade teamFacade = new TeamFacadeImpl();

    @Mock
    private TeamService teamService;

    @Mock
    private BeanMappingService beanMappingService;

    private Team team;

    private TeamDTO teamDTO;

    private TeamCreateDTO teamCreateDTO;

    private List<Team> teams;

    private List<TeamDTO> teamDTOS;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestTeam() {
        team = createTeam();
        teams = new ArrayList<>();
        teams.add(team);

        teamDTO = createTeamDTO();
        teamDTOS = new ArrayList<>();
        teamDTOS.add(teamDTO);

        teamCreateDTO = createTeamCreateDTO();

        when(beanMappingService.mapTo(team, TeamDTO.class)).thenReturn(teamDTO);
        when(beanMappingService.mapTo(teamDTO, Team.class)).thenReturn(team);

        when(beanMappingService.mapTo(team, TeamCreateDTO.class)).thenReturn(teamCreateDTO);
        when(beanMappingService.mapTo(teamCreateDTO, Team.class)).thenReturn(team);

        when(beanMappingService.mapTo(teams, TeamDTO.class)).thenReturn(teamDTOS);
        when(beanMappingService.mapTo(teamDTOS, Team.class)).thenReturn(teams);

    }

    @Test
    public void createTeamTest() {
        when(teamService.createTeam(team)).thenReturn(team);
        teamFacade.createTeam(teamCreateDTO);
        verify(teamService).createTeam(team);
    }

    @Test
    public void deleteTeamTest() {
        doNothing().when(teamService).deleteTeam(team);
        when(teamService.findById(team.getId())).thenReturn(team);
        teamFacade.deleteTeam(team.getId());
        verify(teamService).deleteTeam(team);
    }

    @Test
    public void findAllTeamTest() {
        when(teamService.findAll()).thenReturn(teams);
        Collection<TeamDTO> result = teamFacade.getAllTeams();
        assertThat(result).isEqualTo(teamDTOS);
    }

    @Test
    public void findByCompetitionCountryTeamTest() {
        when(teamService.findByCompetitionCountry(team.getCompetitionCountry()))
                .thenReturn(Collections.singletonList(team));
        List<TeamDTO> result = teamFacade.getTeamsByCountry(teamDTO.getCompetitionCountry());
        assertThat(result).containsExactly(teamDTO);
    }

    @Test
    public void spendMoneyFromBudgetTeamTest() throws TeamServiceException {
        doAnswerSpendMoneyFromBudget();
        when(teamService.findById(team.getId())).thenReturn(team);
        teamFacade.spendMoneyFromBudget(team.getId(), BigDecimal.valueOf(300));
        assertThat(team.getBudget()).isEqualTo(BigDecimal.valueOf(2700));
    }

    @Test
    public void getTeamPriceTeamTest(){
        when(teamService.getTeamPrice(team)).thenReturn(new BigDecimal("30"));
        when(teamService.findById(team.getId())).thenReturn(team);
        BigDecimal price = teamFacade.getTeamPrice(team.getId());
        assertThat(price).isEqualTo(new BigDecimal("30"));
    }

    @Test
    public void getTeamAttackSkillTeamTest(){
        when(teamService.getTeamAttackSkill(team)).thenReturn(50);
        when(teamService.findById(team.getId())).thenReturn(team);
        int attackSkill = teamFacade.getTeamAttackSkill(team.getId());
        assertThat(attackSkill).isEqualTo(50);
    }

    @Test
    public void getTeamDefenseSkillTeamTest(){
        when(teamService.getTeamDefenseSkill(team)).thenReturn(50);
        when(teamService.findById(team.getId())).thenReturn(team);
        int defenseSkill = teamFacade.getTeamDefenseSkill(team.getId());
        assertThat(defenseSkill).isEqualTo(50);
    }

    @Test
    public void findTeamByNameTeamTest(){
        when(teamService.findByName(team.getName())).thenReturn(team);
        TeamDTO result = teamFacade.findTeamByName(teamDTO.getName());
        assertThat(result).isNotNull().isEqualTo(teamDTO);
    }

    private void doAnswerSpendMoneyFromBudget() throws TeamServiceException {
        doAnswer((Answer<Void>) invocationOnMock -> {
            Team t = (Team) invocationOnMock.getArguments()[0];
            t.setBudget(new BigDecimal("2700"));
            return null;
        }).when(teamService).spendMoneyFromBudget(team, new BigDecimal("300"));
    }

    TeamCreateDTO createTeamCreateDTO() {
        TeamCreateDTO teamCreateDTO = new TeamCreateDTO();
        teamCreateDTO.setName("teamTest");
        teamCreateDTO.setBudget(BigDecimal.valueOf(3000));
        teamCreateDTO.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        teamCreateDTO.setHumanPlayerId(1L);
        return teamCreateDTO;
    }

    TeamDTO createTeamDTO() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("teamTest");
        teamDTO.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        return teamDTO;
    }

    Team createTeam() {
        Team team = new Team();
        team.setName("teamTest");
        team.setBudget(BigDecimal.valueOf(3000));
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        return team;
    }

}
