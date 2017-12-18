package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.HockeyPlayerDao;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;
import cz.muni.fi.pa165.exceptions.HockeyPlayerServiceException;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class HockeyPlayerServiceTest extends AbstractTestNGSpringContextTests {

	@Mock
    private HockeyPlayerDao hockeyPlayerDao;

	@Autowired
	@InjectMocks
	private HockeyPlayerService playerService;

	private Team kometa;
	private HockeyPlayer hasek;
	private HockeyPlayer kaberle;
	private HockeyPlayer zidlicky;
	private HockeyPlayer jagr;
	private HockeyPlayer elias;
	private HockeyPlayer hemsky;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeMethod
	public void prepareTestPlayers() throws AuthenticationException {
		hasek = createPlayer("Dominik Hašek", Position.GOALKEEPER, 1, 95, BigDecimal.valueOf(90));
		kaberle = createPlayer("Tomáš Kaberle", Position.DEFENSEMAN, 20, 80, BigDecimal.valueOf(50));
		zidlicky = createPlayer("Marek Židlický", Position.DEFENSEMAN, 25, 70, BigDecimal.valueOf(80));
		jagr = createPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 10, BigDecimal.valueOf(100));
		elias = createPlayer("Patrik Eliáš", Position.CENTER, 70, 65, BigDecimal.valueOf(70));
		hemsky = createPlayer("Aleš Hemský", Position.LEFT_WING, 80, 50, BigDecimal.valueOf(60));

		HumanPlayer owner = new HumanPlayer();
		owner.setUsername("John Brown");
		owner.setEmail("john.brown@gmail.com");
		owner.setPasswordHash("Hash");
		owner.setRole(Role.USER);

		kometa = new Team();
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setName("HC Kometa Brno");
		kometa.setHumanPlayer(owner);
		kometa.setBudget(BigDecimal.valueOf(600));
		kometa.addHockeyPlayer(hasek);
		kometa.addHockeyPlayer(kaberle);
		kometa.addHockeyPlayer(jagr);

		playerService.create(hasek);
		playerService.create(kaberle);
		playerService.create(zidlicky);
		playerService.create(jagr);
		playerService.create(elias);
		playerService.create(hemsky);
	}

	@Test
	public void findByPriceLessThanTest() {
        when(hockeyPlayerDao.findByPriceLessOrEqualThan(BigDecimal.valueOf(69)))
                .thenReturn(Arrays.asList(hemsky, kaberle));
		List<HockeyPlayer> cheapPlayers = playerService.findByPriceLessOrEqualThan(BigDecimal.valueOf(69));
		assertThat(cheapPlayers).containsExactlyInAnyOrder(hemsky, kaberle);
	}

	@Test
	public void findByTeamTest() {
        when(hockeyPlayerDao.findByTeam(kometa)).thenReturn(Arrays.asList(hasek, kaberle, jagr));
		List<HockeyPlayer> kometaPlayers = playerService.findByTeam(kometa);
		assertThat(kometaPlayers).containsExactlyInAnyOrder(hasek, kaberle, jagr);
	}

	@Test
	public void findFreeAgentsTest() {
        when(hockeyPlayerDao.findFreeAgents()).thenReturn(Arrays.asList(zidlicky, elias, hemsky));
		List<HockeyPlayer> freeAgents = playerService.findFreeAgents();
		assertThat(freeAgents).containsExactlyInAnyOrder(zidlicky, elias, hemsky);
	}

	@Test
	public void findByPostAndPriceTest() {
        when(hockeyPlayerDao.findByPriceLessOrEqualThan(BigDecimal.valueOf(79)))
                .thenReturn(Collections.singletonList(kaberle));
		List<HockeyPlayer> cheapDefenders = playerService.findByPostAndPrice(Position.DEFENSEMAN, BigDecimal.valueOf(79));
		assertThat(cheapDefenders).containsExactlyInAnyOrder(kaberle);
	}

	@Test
	public void findByAttSkillTest() {
        when(hockeyPlayerDao.findAll()).thenReturn(Arrays.asList(hasek, kaberle, jagr, hemsky, zidlicky, elias));
        List<HockeyPlayer> attackers = playerService.findByAttSkill(80);
        assertThat(attackers).containsExactlyInAnyOrder(jagr, hemsky);
	}

	@Test
	public void findByInvalidAttSkillTest() {
		assertThatThrownBy(() -> playerService.findByAttSkill(0))
                .isInstanceOf(HockeyPlayerServiceException.class);
	}

	@Test
	public void findByDefSkillTest() {
        when(hockeyPlayerDao.findAll()).thenReturn(Arrays.asList(hasek, kaberle, jagr, hemsky, zidlicky, elias));
        List<HockeyPlayer> defenders = playerService.findByDefSkill(80);
        assertThat(defenders).containsExactlyInAnyOrder(hasek, kaberle);
	}

	@Test
	public void findByInvalidDefSkillTest() {
		assertThatThrownBy(() -> playerService.findByDefSkill(0))
                .isInstanceOf(HockeyPlayerServiceException.class);
	}

	private HockeyPlayer createPlayer(String name, Position post, int attSkill, int defSkill, BigDecimal price) {
		HockeyPlayer player = new HockeyPlayer();
		player.setName(name);
		player.setPost(post);
		player.setAttackSkill(attSkill);
		player.setDefenseSkill(defSkill);
		player.setPrice(price);
		return player;
	}
}
