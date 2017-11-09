package cz.muni.fi.pa165.dao;


import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TeamDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private HockeyPlayerDao hockeyPlayerDao;

	@Autowired
	private HumanPlayerDao humanPlayerDao;

	@Test
	void createMinimalParameters() {
		Team team = new Team();
		team.setName("HC Kometa Brno");
		team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		team.setBudget(new BigDecimal("5000"));

		teamDao.create(team);

		List<Team> foundTeams = teamDao.findAll();

		assertThat(foundTeams.size())
				.isEqualTo(1);
		assertThat(foundTeams.get(0).getName())
				.isEqualTo("HC Kometa Brno");
		assertThat(foundTeams.get(0).getCompetitionCountry())
				.isEqualTo(CompetitionCountry.CZECH_REPUBLIC);
		assertThat(foundTeams.get(0).getBudget())
				.isEqualTo(new BigDecimal("5000"));
	}

	@Test
	void createWithNullName() {
		Team team = new Team();
		team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		team.setBudget(new BigDecimal("5000"));

		assertThatThrownBy(() -> teamDao.create(team)).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	void createWithNullCountry() {
		Team team = new Team();
		team.setName("HC Kometa Brno");
		team.setBudget(new BigDecimal("5000"));

		assertThatThrownBy(() -> teamDao.create(team)).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	void createWithNullBudget() {
		Team team = new Team();
		team.setName("HC Kometa Brno");
		team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);

		assertThatThrownBy(() -> teamDao.create(team)).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	void createWithNonUniqueName() {
		Team team1 = new Team();
		team1.setName("HC Kometa Brno");
		team1.setBudget(new BigDecimal("5000"));
		team1.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		teamDao.create(team1);

		Team team2 = new Team();
		team2.setName("HC Kometa Brno");
		team2.setBudget(new BigDecimal("5000"));
		team2.setCompetitionCountry(CompetitionCountry.FINLAND);

		assertThatThrownBy(() -> teamDao.create(team2)).isInstanceOf(DataIntegrityViolationException.class);
	}

	@Test
	void createWithBudgetLessThanZero() {
		Team team = new Team();
		team.setName("HC Kometa Brno");
		team.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		team.setBudget(new BigDecimal("-5"));

		assertThatThrownBy(() -> teamDao.create(team)).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	void createWithAllAttributes() {
		Team kometa = new Team();
		kometa.setName("HC Kometa Brno");
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setBudget(new BigDecimal("5000"));

		HockeyPlayer captain = new HockeyPlayer();
		captain.setName("Leoš Čermák");
		captain.setPost(Position.C);
		captain.setAttackSkill(50);
		captain.setDefenseSkill(50);
		captain.setPrice(new BigDecimal("100"));
		hockeyPlayerDao.save(captain);
		HashSet<HockeyPlayer> players = new HashSet<>();
		players.add(captain);
		kometa.setHockeyPlayers(players);
		captain.setTeam(kometa);

		HumanPlayer user = new HumanPlayer();
		user.setUsername("username");
		user.setEmail("username@email.com");
		user.setPasswordHash("pass");
		user.setRole(Role.USER);
		humanPlayerDao.save(user);
		kometa.setHumanPlayer(user);
		user.setTeam(kometa);

		teamDao.create(kometa);
		List<Team> foundTeams = teamDao.findAll();
		assertThat(foundTeams.size()).isEqualTo(1);
		Team foundTeam = foundTeams.get(0);

		assertThat(foundTeam.getName())
				.isEqualTo("HC Kometa Brno");
		assertThat(foundTeam.getCompetitionCountry())
				.isEqualTo(CompetitionCountry.CZECH_REPUBLIC);
		assertThat(foundTeam.getBudget())
				.isEqualTo(new BigDecimal("5000"));
		assertThat(foundTeam.getHumanPlayer())
				.isEqualTo(user);
		assertThat(foundTeam.getHockeyPlayers())
				.contains(captain);
	}

	@Test
	void idCorrectlyGenerated() {
		Team kometa = new Team();
		kometa.setName("HC Kometa Brno");
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setBudget(new BigDecimal("5000"));
		teamDao.create(kometa);

		Team sparta = new Team();
		sparta.setName("HC Sparta Praha");
		sparta.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		sparta.setBudget(new BigDecimal("8000"));
		teamDao.create(sparta);

		Team foundKometa = teamDao.findByName("HC Kometa Brno");
		Team foundSparta = teamDao.findByName("HC Sparta Praha");

		assertThat(foundKometa.getId())
				.isNotNull();
		assertThat(foundSparta.getId())
				.isNotNull();
		assertThat(foundKometa.getId())
				.isNotEqualTo(foundSparta.getId());
	}

	@Test
	void findByCompetitionCountry() {
		Team kometa = new Team();
		kometa.setName("HC Kometa Brno");
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setBudget(new BigDecimal("5000"));
		teamDao.create(kometa);

		Team sparta = new Team();
		sparta.setName("HC Sparta Praha");
		sparta.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		sparta.setBudget(new BigDecimal("8000"));
		teamDao.create(sparta);

		Team slovan = new Team();
		slovan.setName("HC Slovan Bratislava");
		slovan.setCompetitionCountry(CompetitionCountry.SLOVAKIA);
		slovan.setBudget(new BigDecimal("10000"));
		teamDao.create(slovan);

		List<Team> czechTeams = teamDao.findByCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);

		assertThat(czechTeams.size())
				.isEqualTo(2);
		assertThat(czechTeams)
				.contains(kometa);
		assertThat(czechTeams)
				.doesNotContain(slovan);
	}

	@Test
	void updateAndFindById() {
		Team kometa = new Team();
		kometa.setName("HC Kometa Brno");
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setBudget(new BigDecimal("5000"));
		teamDao.create(kometa);

		kometa.setCompetitionCountry(CompetitionCountry.SLOVAKIA);
		Team updatedKometa = teamDao.update(kometa);

		assertThat(updatedKometa.getName())
				.isEqualTo("HC Kometa Brno");
		assertThat(updatedKometa.getCompetitionCountry())
				.isEqualTo(CompetitionCountry.SLOVAKIA);

		Team foundKometa = teamDao.findById(updatedKometa.getId());

		assertThat(foundKometa.getName())
				.isEqualTo(updatedKometa.getName());
		assertThat(foundKometa.getCompetitionCountry())
				.isEqualTo(updatedKometa.getCompetitionCountry());
	}

	@Test
	void delete() {
		Team kometa = new Team();
		kometa.setName("HC Kometa Brno");
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setBudget(new BigDecimal("5000"));
		teamDao.create(kometa);
		assertThat(teamDao.findById(kometa.getId()))
				.isNotNull();
		teamDao.delete(kometa);
		assertThat(teamDao.findById(kometa.getId()))
				.isNull();
	}
}
