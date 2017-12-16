package cz.muni.fi.pa165.service.facade;


import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.service.HockeyPlayerService;
import cz.muni.fi.pa165.service.HumanPlayerService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
    private HumanPlayerService humanPlayerService;

    @Mock
    private HockeyPlayerService hockeyPlayerService;

    @Mock
    private BeanMappingService beanMappingService;

    private Team team;

    private TeamDto teamDto;

    private HumanPlayer humanPlayer;

    private HockeyPlayer hockeyPlayer;

    private List<TeamDto> teamDtoS;

    private List<Team> teams;

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

        humanPlayer = createHumanPlayer();

        hockeyPlayer = createHockeyPlayer();

        when(beanMappingService.mapTo(team, TeamDto.class)).thenReturn(teamDto);
        when(beanMappingService.mapTo(teamDto, Team.class)).thenReturn(team);

        when(beanMappingService.mapTo(teams, TeamDto.class)).thenReturn(teamDtoS);
        when(beanMappingService.mapTo(teamDtoS, Team.class)).thenReturn(teams);

    }

    @Test
    public void createTeamTest() {
        when(teamService.createTeam(Mockito.any(Team.class))).thenReturn(team);
        when(humanPlayerService.findById(Mockito.anyLong())).thenReturn(humanPlayer);

        assertThat(teamFacade.createTeam(teamDto)).isEqualTo(1L);

        verify(teamService).createTeam(team);
    }

    @Test
    public void deleteTeamTest() {
        doNothing().when(teamService).deleteTeam(team);
        when(teamService.findById(Mockito.anyLong())).thenReturn(team);

        teamFacade.deleteTeam(teamDto.getId());

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
    public void getTeamPriceTeamTest() {
        when(teamService.getTeamPrice(team)).thenReturn(new BigDecimal("30"));
        when(teamService.findById(team.getId())).thenReturn(team);
        BigDecimal price = teamFacade.getTeamPrice(team.getId());
        assertThat(price).isEqualTo(new BigDecimal("30"));
    }

    @Test
    public void getTeamAttackSkillTeamTest() {
        when(teamService.getTeamAttackSkill(team)).thenReturn(50);
        when(teamService.findById(team.getId())).thenReturn(team);
        int attackSkill = teamFacade.getTeamAttackSkill(team.getId());
        assertThat(attackSkill).isEqualTo(50);
    }

    @Test
    public void getTeamDefenseSkillTeamTest() {
        when(teamService.getTeamDefenseSkill(team)).thenReturn(50);
        when(teamService.findById(team.getId())).thenReturn(team);
        int defenseSkill = teamFacade.getTeamDefenseSkill(team.getId());
        assertThat(defenseSkill).isEqualTo(50);
    }

    @Test
    public void findTeamByNameTeamTest() {
        when(teamService.findByName(team.getName())).thenReturn(team);
        TeamDto result = teamFacade.findTeamByName(teamDto.getName());
        assertThat(result).isNotNull().isEqualTo(teamDto);
    }

    @Test
    public void addHockeyPlayerTest() {
        when(teamService.findById(Mockito.anyLong())).thenReturn(team);
        when(hockeyPlayerService.findById(Mockito.anyLong())).thenReturn(hockeyPlayer);
        doNothing().when(teamService).addHockeyPlayer(Mockito.any(Team.class), Mockito.any(HockeyPlayer.class));

        teamFacade.addHockeyPlayer(team.getId(), hockeyPlayer.getId());

        verify(teamService).addHockeyPlayer(team, hockeyPlayer);
    }


    @Test
    public void removeHockeyPlayerTest() {
        when(teamService.findById(Mockito.anyLong())).thenReturn(team);
        when(hockeyPlayerService.findById(Mockito.anyLong())).thenReturn(hockeyPlayer);
        doNothing().when(teamService).removeHockeyPlayer(Mockito.any(Team.class), Mockito.any(HockeyPlayer.class));

        teamFacade.removeHockeyPlayer(team.getId(), hockeyPlayer.getId());

        verify(teamService).removeHockeyPlayer(team, hockeyPlayer);
    }

    private void doAnswerSpendMoneyFromBudget() throws TeamServiceException {
        doAnswer((Answer<Void>) invocationOnMock -> {
            Team t = (Team) invocationOnMock.getArguments()[0];
            t.setBudget(new BigDecimal("2700"));
            return null;
        }).when(teamService).spendMoneyFromBudget(team, new BigDecimal("300"));
    }

    private TeamDto createTeamDto() {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("teamTest");
        teamDto.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        teamDto.setBudget(BigDecimal.valueOf(3000.00).setScale(2));
        teamDto.setHumanPlayerId(1L);
        return teamDto;
    }

    private Team createTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setName("teamTest");
        team.setBudget(BigDecimal.valueOf(3000.00).setScale(2));
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        return team;
    }

    private HumanPlayer createHumanPlayer() {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setRole(Role.USER);
        humanPlayer.setPasswordHash("asdoaskdoaksd");
        humanPlayer.setEmail("x@x.com");
        humanPlayer.setUsername("User");
        humanPlayer.setId(1L);
        return humanPlayer;
    }

    private HockeyPlayer createHockeyPlayer() {
        HockeyPlayer hockeyPlayer = new HockeyPlayer();
        hockeyPlayer.setAttackSkill(10);
        hockeyPlayer.setDefenseSkill(50);
        hockeyPlayer.setPost(Position.DEFENSEMAN);
        hockeyPlayer.setName("Defender");
        hockeyPlayer.setTeam(team);
        hockeyPlayer.setPrice(BigDecimal.valueOf(100));
        hockeyPlayer.setId(1L);
        return  hockeyPlayer;
    }


}
