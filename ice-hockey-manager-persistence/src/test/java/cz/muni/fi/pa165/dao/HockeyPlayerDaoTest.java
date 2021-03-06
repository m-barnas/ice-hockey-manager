package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HockeyPlayerDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HockeyPlayerDao hockeyPlayerDao;

    @Autowired
    private TeamDao teamDao;

    private HockeyPlayer hockeyPlayer;

    @BeforeMethod
    public void setUp() {
        hockeyPlayer = new HockeyPlayer();
        hockeyPlayer.setName("Jan Novák");
        hockeyPlayer.setPost(Position.LEFT_WING);
        hockeyPlayer.setAttackSkill(10);
        hockeyPlayer.setDefenseSkill(23);
        hockeyPlayer.setPrice(new BigDecimal("0"));
    }

    @Test
    public void createHockeyPlayer() {
        hockeyPlayerDao.create(hockeyPlayer);
        assertThat(hockeyPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(hockeyPlayerDao.findById(hockeyPlayer.getId())).isEqualToComparingFieldByField(hockeyPlayer);
    }

    @Test
    public void createHockeyPlayerNullName() {
        hockeyPlayer.setName(null);
        assertThatThrownBy(() -> hockeyPlayerDao.create(hockeyPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void createHockeyPlayerNullPosition() {
        hockeyPlayer.setPost(null);
        assertThatThrownBy(() -> hockeyPlayerDao.create(hockeyPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void updateHockeyPlayer() {
        hockeyPlayerDao.create(hockeyPlayer);
        hockeyPlayer.setName("Updated");
        hockeyPlayer.setPost(Position.CENTER);
        hockeyPlayer.setAttackSkill(30);
        hockeyPlayer.setDefenseSkill(3);
        hockeyPlayerDao.update(hockeyPlayer);
        assertThat(hockeyPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(hockeyPlayerDao.findById(hockeyPlayer.getId())).isEqualToComparingFieldByField(hockeyPlayer);
    }

    @Test
    public void deleteHockeyPlayer() {
        hockeyPlayerDao.create(hockeyPlayer);
        hockeyPlayerDao.delete(hockeyPlayer);
        assertThat(hockeyPlayerDao.findAll()).isEmpty();
    }

    @Test
    public void findByNameHockeyPlayer() {
        hockeyPlayerDao.create(hockeyPlayer);
        assertThat(hockeyPlayerDao.findByName("Jan Novák")).isEqualToComparingFieldByField(hockeyPlayer);
    }

    @Test
    public void findByPostHockeyPlayer() {
        hockeyPlayerDao.create(hockeyPlayer);
        assertThat(hockeyPlayerDao.findByPost(Position.LEFT_WING)).contains(hockeyPlayer);
    }

    @Test
    public void findByTeamHockeyPlayer() {
        Team team = new Team();
        team.setName("BestTeam");
        team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
        team.setHockeyPlayers(new HashSet<>(Arrays.asList(hockeyPlayer)));
        team.setBudget(new BigDecimal("0"));

        hockeyPlayer.setTeam(team);
        teamDao.create(team);
        hockeyPlayerDao.create(hockeyPlayer);

        assertThat(hockeyPlayerDao.findByTeam(team)).contains(hockeyPlayer);
    }


    @Test
    public void findFreeAgents() {
        hockeyPlayer.setTeam(null);
        hockeyPlayerDao.create(hockeyPlayer);

        assertThat(hockeyPlayerDao.findFreeAgents()).contains(hockeyPlayer);
    }


    @Test
    public void findAll() {
        hockeyPlayerDao.create(hockeyPlayer);
        hockeyPlayerDao.create(createHockeyPlayerByName("Petr Rychlý"));
        hockeyPlayerDao.create(createHockeyPlayerByName("Ivan Pomalý"));
        assertThat(hockeyPlayerDao.findAll().size()).isEqualTo(3);
    }

    private static HockeyPlayer createHockeyPlayerByName(String name) {
        HockeyPlayer player = new HockeyPlayer();
        player.setName(name);
        player.setPost(Position.LEFT_WING);
        player.setAttackSkill(10);
        player.setDefenseSkill(23);
        player.setPrice(new BigDecimal("0"));
        return player;
    }

}
