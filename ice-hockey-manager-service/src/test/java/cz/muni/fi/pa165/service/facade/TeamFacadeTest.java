package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.TeamCreateDto;
import cz.muni.fi.pa165.dto.TeamDto;
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

    private TeamDto teamDto;

    private TeamCreateDto teamCreateDto;

    private List<Team> teams;

    private List<TeamDto> teamDtoS;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestTeam() {
        team = createTeam();
        teams = new ArrayList<>();
        teams.add(team);

        teamDto = createTeamDto();
        teamDtoS = new ArrayList<>();
        teamDtoS.add(teamDto);

        teamCreateDto = createTeamCreateDto();

        when(beanMappingService.mapTo(team, TeamDto.class)).thenReturn(teamDto);
        when(beanMappingService.mapTo(teamDto, Team.class)).thenReturn(team);

        when(beanMappingService.mapTo(team, TeamCreateDto.class)).thenReturn(teamCreateDto);
        when(beanMappingService.mapTo(teamCreateDto, Team.class)).thenReturn(team);

        when(beanMappingService.mapTo(teams, TeamDto.class)).thenReturn(teamDtoS);
        when(beanMappingService.mapTo(teamDtoS, Team.class)).thenReturn(teams);

    }

    @Test
    public void createTeamTest() {
        when(teamService.createTeam(team)).thenReturn(team);
        teamFacade.createTeam(teamCreateDto);
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
        Collection<TeamDto> result = teamFacade.getAllTeams();
        assertThat(result).isEqualTo(teamDtoS);
    }

    @Test
    public void findByCompetitionCountryTeamTest() {
        when(teamService.findByCompetitionCountry(team.getCompetitionCountry()))
                .thenReturn(Collections.singletonList(team));
        List<TeamDto> result = teamFacade.getTeamsByCountry(teamDto.getCompetitionCountry());
        assertThat(result).containsExactly(teamDto);
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
        TeamDto result = teamFacade.findTeamByName(teamDto.getName());
        assertThat(result).isNotNull().isEqualTo(teamDto);
    }

    private void doAnswerSpendMoneyFromBudget() throws TeamServiceException {
        doAnswer((Answer<Void>) invocationOnMock -> {
            Team t = (Team) invocationOnMock.getArguments()[0];
            t.setBudget(new BigDecimal("2700"));
            return null;
        }).when(teamService).spendMoneyFromBudget(team, new BigDecimal("300"));
    }

    TeamCreateDto createTeamCreateDto() {
        TeamCreateDto teamCreateDto = new TeamCreateDto();
        teamCreateDto.setName("teamTest");
        teamCreateDto.setBudget(BigDecimal.valueOf(3000));
        teamCreateDto.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        teamCreateDto.setHumanPlayerId(1L);
        return teamCreateDto;
    }

    TeamDto createTeamDto() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("teamTest");
        teamDto.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        return teamDto;
    }

    Team createTeam() {
        Team team = new Team();
        team.setName("teamTest");
        team.setBudget(BigDecimal.valueOf(3000));
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        return team;
    }
}
