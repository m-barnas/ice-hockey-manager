package cz.muni.fi.pa165.service;


import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.dao.TeamDao;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.enums.Role;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TeamServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TeamDao teamDao;

    @Autowired
    @InjectMocks
    private TeamService teamService;

    private Team team;

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

        Team team = new Team();
        team.setName("Team");
        team.setHumanPlayer(humanPlayer);
        team.setBudget(BigDecimal.valueOf(500));
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        team.setHockeyPlayers(new HashSet<HockeyPlayer>(Arrays.asList(hockeyPlayerOne, hockeyPlayerTwo)));

        hockeyPlayerOne.setTeam(team);
        hockeyPlayerTwo.setTeam(team);
        humanPlayer.setTeam(team);

        this.team = team;
    }

    @Test
    public void createTeam() {
        teamService.createTeam(team);

    }

    private HockeyPlayer createHockeyPlayer(String name) {
        HockeyPlayer hockeyPlayer = new HockeyPlayer();
        hockeyPlayer.setName(name);
        hockeyPlayer.setAttackSkill(10);
        hockeyPlayer.setDefenseSkill(5);
        hockeyPlayer.setPost(Position.C);
        hockeyPlayer.setPrice(BigDecimal.TEN);
        return hockeyPlayer;
    }

}
