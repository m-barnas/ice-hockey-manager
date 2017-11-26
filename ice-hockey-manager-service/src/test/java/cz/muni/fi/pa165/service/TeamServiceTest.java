package cz.muni.fi.pa165.service;


import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.dao.HockeyPlayerDao;
import cz.muni.fi.pa165.dao.HumanPlayerDao;
import cz.muni.fi.pa165.dao.TeamDao;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TeamServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TeamDao teamDao;

    @Autowired
    private TeamService teamService;

    @Autowired
    private HumanPlayerDao humanPlayerDao;

    @Autowired
    private HockeyPlayerDao hockeyPlayerDao;

    private Team team1;
    private Team team2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareTestTeam() {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setUsername("John Brown");
        humanPlayer.setEmail("john.brown@gmail.com");
        humanPlayer.setPasswordHash("Hash");
        humanPlayer.setRole(Role.USER);

        HockeyPlayer hockeyPlayerOne = createHockeyPlayer("Jaromír Jágr");
        HockeyPlayer hockeyPlayerTwo = createHockeyPlayer("Patrik Eliáš");

        team1 = createTeam("TeamField", humanPlayer, new HashSet<HockeyPlayer>(Arrays.asList(hockeyPlayerOne, hockeyPlayerTwo)));
        team2 = createTeam("TestTeam", null, null);
        hockeyPlayerOne.setTeam(team1);
        hockeyPlayerTwo.setTeam(team1);
        humanPlayer.setTeam(team1);
        hockeyPlayerDao.create(hockeyPlayerOne);
        hockeyPlayerDao.create(hockeyPlayerTwo);
        humanPlayerDao.create(humanPlayer);
    }

    @Test
    public void createTeamTest() {
        teamService.createTeam(team1);
        assertThat(teamService.findById(team1.getId())).isEqualToComparingFieldByField(team1);
    }

    @Test
    public void findTeamByNameTest() {
        teamService.createTeam(team1);
        when(teamDao.findByName(Mockito.anyString())).thenReturn(team1);
        Team foundTeam = teamService.findByName(team1.getName());
        assertThat(foundTeam).isEqualToComparingFieldByField(team1);
    }

    @Test
    public void findAllTest() {
        teamService.createTeam(team1);
        teamService.createTeam(team2);
        when(teamDao.findAll()).thenReturn(Arrays.asList(team1, team2));
        List<Team> foundTeams = teamService.findAll();
        assertThat(foundTeams).containsExactly(team1, team2);
    }

    @Test
    public void findByCompetitionCountryTest() {
        teamService.createTeam(team1);
        teamService.createTeam(team2);
        when(teamDao.findByCompetitionCountry(Mockito.any(CompetitionCountry.class))).thenReturn(Arrays.asList(team1, team2));
        List<Team> foundTeams = teamService.findByCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        assertThat(foundTeams).containsExactly(team1, team2);
    }

    @Test
    public void spendMoneyFromBudget() throws TeamServiceException {
        teamService.createTeam(team1);
        teamService.spendMoneyFromBudget(team1, BigDecimal.valueOf(200));
        assertThat(team1.getBudget()).isEqualTo(BigDecimal.valueOf(300));
    }

    @Test
    public void spendMoneyFromBudgetLessThanZeroTest() {
        teamService.createTeam(team1);
        assertThatThrownBy(() -> teamService.spendMoneyFromBudget(team1, BigDecimal.valueOf(1000))).isInstanceOf(TeamServiceException.class);
    }

    @Test
    public void spendMoneyFromBudgetNullTest() {
        teamService.createTeam(team1);
        assertThatThrownBy(() -> teamService.spendMoneyFromBudget(team1, null)).isInstanceOf(TeamServiceException.class);
    }

    @Test
    public void getTeamAttackSkillTest() {
        teamService.createTeam(team1);
        assertThat(teamService.getTeamAttackSkill(team1)).isEqualTo(20);
    }

    @Test
    public void getTeamDefenseSkillTest() {
        teamService.createTeam(team1);
        assertThat(teamService.getTeamDefenseSkill(team1)).isEqualTo(10);
    }


    private HockeyPlayer createHockeyPlayer(String name) {
        HockeyPlayer hockeyPlayer = new HockeyPlayer();
        hockeyPlayer.setName(name);
        hockeyPlayer.setAttackSkill(10);
        hockeyPlayer.setDefenseSkill(5);
        hockeyPlayer.setPost(Position.CENTER);
        hockeyPlayer.setPrice(BigDecimal.TEN);
        return hockeyPlayer;
    }

    private Team createTeam(String name, HumanPlayer humanPlayer, Set<HockeyPlayer> hockeyPlayers) {
        Team team = new Team();
        team.setName(name);
        team.setHumanPlayer(humanPlayer);
        team.setBudget(BigDecimal.valueOf(500));
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        team.setHockeyPlayers(hockeyPlayers);
        return team;
    }

}
