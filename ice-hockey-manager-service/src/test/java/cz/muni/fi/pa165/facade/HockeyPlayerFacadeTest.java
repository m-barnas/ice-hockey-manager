package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.facade.TeamFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HockeyPlayerFacadeTest {

	@Autowired
	@InjectMocks
	private HockeyPlayerFacade playerFacade;

	@Mock
	private TeamService teamService;

	@Mock
	private TeamDTO testTeam;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeMethod
	public void prepareTestTeam() {
		testTeam = new TeamDTO();
		testTeam.setName("testTeam");
		testTeam.setBudget(BigDecimal.valueOf(3000));
		testTeam.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
	}

	private HockeyPlayerDto createHockeyPlayer(String name, Position post, int attSkill, int defSkill, boolean isInTeam, BigDecimal price) {
		HockeyPlayerDto player = new HockeyPlayerDto();
		player.setName(name);
		player.setPost(post);
		player.setAttackSkill(attSkill);
		player.setDefenseSkill(defSkill);
		player.setPrice(price);
		if (isInTeam) {
			player.setTeam(testTeam);
		}
		return player;
	}

	@Test
	public void createTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		Long id = playerFacade.create(jagr);
		assertThat(playerFacade.findById(id).getName()).isEqualTo("Jaromír Jágr");
		assertThat(playerFacade.findById(id).getPrice()).isEqualTo(BigDecimal.valueOf(50));
		assertThat(playerFacade.findById(id).getTeam().getName()).isEqualTo("testTeam");
	}

	@Test
	public void deleteTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		Long id = playerFacade.create(jagr);
		assertThat(playerFacade.findById(id).getName()).isEqualTo("Jaromír Jágr");
		playerFacade.delete(id);
		assertThat(playerFacade.findById(id)).isNull();
	}

	@Test
	public void findByNameTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		playerFacade.create(jagr);
		assertThat(playerFacade.findByName("Jaromír Jágr")).isEqualToComparingFieldByField(jagr);
	}

	@Test
	public void findByPostTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, true, BigDecimal.valueOf(50));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(50));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByPost(Position.DEFENSEMAN)).containsExactlyInAnyOrder(rutta, kempny);
	}

	@Test
	public void updateTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		playerFacade.create(jagr);
		jagr.setAttackSkill(90);
		assertThat(playerFacade.update(jagr).getAttackSkill()).isEqualTo(90);
	}

	@Test
	public void findByTeamTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(50));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(50));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByTeam(testTeam)).containsExactlyInAnyOrder(jagr, kempny);
	}

	@Test
	public void findByPriceTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, true, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByPriceLessOrEqualThan(BigDecimal.valueOf(40))).containsExactlyInAnyOrder(kempny, rutta);
	}

	@Test
	public void findFreeAgentsTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findFreeAgents()).containsExactlyInAnyOrder(jagr, rutta);
	}

	@Test
	public void findByPostAndPrice() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByPostAndPrice(Position.DEFENSEMAN, BigDecimal.valueOf(30))).containsExactly(kempny);
	}

	@Test
	public void findByAttSkillTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByAttSkill(50)).containsExactlyInAnyOrder(jagr, rutta);
	}

	@Test
	public void findByDeffSkillTest() {
		HockeyPlayerDto jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		HockeyPlayerDto rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		HockeyPlayerDto kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		playerFacade.create(jagr);
		playerFacade.create(rutta);
		playerFacade.create(kempny);
		assertThat(playerFacade.findByDefSkill(60)).containsExactlyInAnyOrder(rutta, kempny);
	}
}
