package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.HockeyPlayerFacade;
import cz.muni.fi.pa165.service.HockeyPlayerService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HockeyPlayerFacadeTest extends AbstractTestNGSpringContextTests {

	@Mock
	private HockeyPlayerService hockeyPlayerService;

	@Mock
	private BeanMappingService beanMappingService;

	@Mock
	private TeamService teamService;

	@InjectMocks
	private HockeyPlayerFacade hockeyPlayerFacade = new HockeyPlayerFacadeImpl();

	private HockeyPlayer jagr;
	private HockeyPlayer kempny;
	private HockeyPlayer rutta;
	private HockeyPlayerDto jagrDto;
	private HockeyPlayerDto kempnyDto;
	private HockeyPlayerDto ruttaDto;
	private Team testTeam;
	private TeamDto testTeamDto;

	private List<HockeyPlayer> hockeyPlayers;
	private List<HockeyPlayerDto> hockeyPlayerDtos;

	@BeforeClass
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeMethod
	public void prepareData() {
		testTeam = new Team();
		testTeam.setName("testTeam");

		testTeamDto = new TeamDto();
		testTeamDto.setName("testTeam");

		hockeyPlayers = new ArrayList<>();
		hockeyPlayerDtos = new ArrayList<>();

		jagr = createHockeyPlayer("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		jagrDto = createHockeyPlayerDto("Jaromír Jágr", Position.RIGHT_WING, 99, 5, false, BigDecimal.valueOf(50));
		kempny = createHockeyPlayer("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		kempnyDto = createHockeyPlayerDto("Michal Kempný", Position.DEFENSEMAN, 40, 70, true, BigDecimal.valueOf(30));
		rutta = createHockeyPlayer("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));
		ruttaDto = createHockeyPlayerDto("Jan Rutta", Position.DEFENSEMAN, 50, 60, false, BigDecimal.valueOf(40));

		when(beanMappingService.mapTo(jagr, HockeyPlayerDto.class)).thenReturn(jagrDto);
		when(beanMappingService.mapTo(jagrDto, HockeyPlayer.class)).thenReturn(jagr);

		when(beanMappingService.mapTo(kempny, HockeyPlayerDto.class)).thenReturn(kempnyDto);
		when(beanMappingService.mapTo(kempnyDto, HockeyPlayer.class)).thenReturn(kempny);

		when(beanMappingService.mapTo(rutta, HockeyPlayerDto.class)).thenReturn(ruttaDto);
		when(beanMappingService.mapTo(ruttaDto, HockeyPlayer.class)).thenReturn(rutta);
	}


	@Test
	public void createTest() {
		// setup
        when(hockeyPlayerService.create(jagr)).thenReturn(1L);

        // exercise
        Long resultId = hockeyPlayerFacade.create(jagrDto);

        // verify
        assertThat(resultId).isNotNull().isEqualTo(1L);
	}

	@Test
	public void deleteTest() {
		// setup
		doNothing().when(hockeyPlayerService).delete(jagr);
		when(hockeyPlayerService.findById(jagr.getId())).thenReturn(jagr);

		// exercise
		hockeyPlayerFacade.delete(jagrDto.getId());

		// verify
		verify(hockeyPlayerService).delete(jagr);
	}

	@Test
	public void findByNameTest() {
		// setup
		when(hockeyPlayerService.findByName(jagr.getName())).thenReturn(jagr);

		// exercise
		HockeyPlayerDto result = hockeyPlayerFacade.findByName(jagrDto.getName());

		// verify
		assertThat(result).isEqualToComparingFieldByField(jagrDto);
	}

	@Test
	public void findByPostTest() {
		// setup
		hockeyPlayers.add(kempny);
		hockeyPlayers.add(rutta);
		hockeyPlayerDtos.add(kempnyDto);
		hockeyPlayerDtos.add(ruttaDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByPost(Position.DEFENSEMAN)).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByPost(Position.DEFENSEMAN);

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findByTeamTest() {
		// setup
		hockeyPlayers.add(kempny);
		hockeyPlayerDtos.add(kempnyDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByTeam(testTeam)).thenReturn(hockeyPlayers);
		when(teamService.findByName(testTeam.getName())).thenReturn(testTeam);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByTeam(testTeamDto);

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findByPriceTest() {
		// setup
		hockeyPlayers.add(kempny);
		hockeyPlayers.add(rutta);
		hockeyPlayerDtos.add(kempnyDto);
		hockeyPlayerDtos.add(ruttaDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByPriceLessOrEqualThan(BigDecimal.valueOf(40))).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByPriceLessOrEqualThan(BigDecimal.valueOf(40));

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findFreeAgentsTest() {
		hockeyPlayers.add(jagr);
		hockeyPlayers.add(rutta);
		hockeyPlayerDtos.add(jagrDto);
		hockeyPlayerDtos.add(ruttaDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findFreeAgents()).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findFreeAgents();

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findByPostAndPrice() {
		// setup
		hockeyPlayers.add(kempny);
		hockeyPlayerDtos.add(kempnyDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByPostAndPrice(Position.DEFENSEMAN, BigDecimal.valueOf(30))).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByPostAndPrice(Position.DEFENSEMAN, BigDecimal.valueOf(30));

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findByAttSkillTest() {
		hockeyPlayers.add(jagr);
		hockeyPlayers.add(rutta);
		hockeyPlayerDtos.add(jagrDto);
		hockeyPlayerDtos.add(ruttaDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByAttSkill(50)).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByAttSkill(50);

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	@Test
	public void findByDeffSkillTest() {
		hockeyPlayers.add(kempny);
		hockeyPlayers.add(rutta);
		hockeyPlayerDtos.add(kempnyDto);
		hockeyPlayerDtos.add(ruttaDto);
		when(beanMappingService.mapTo(hockeyPlayers, HockeyPlayerDto.class)).thenReturn(hockeyPlayerDtos);
		when(beanMappingService.mapTo(hockeyPlayerDtos, HockeyPlayer.class)).thenReturn(hockeyPlayers);
		when(hockeyPlayerService.findByDefSkill(60)).thenReturn(hockeyPlayers);

		// exercise
		Collection<HockeyPlayerDto> result = hockeyPlayerFacade.findByDefSkill(60);

		// verify
		assertThat(result).isEqualTo(hockeyPlayerDtos);
	}

	private HockeyPlayer createHockeyPlayer(String name, Position post, int attSkill, int defSkill, boolean isInTeam, BigDecimal price) {
		HockeyPlayer player = new HockeyPlayer();
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

	private HockeyPlayerDto createHockeyPlayerDto(String name, Position post, int attSkill, int defSkill, boolean isInTeam, BigDecimal price) {
		HockeyPlayerDto player = new HockeyPlayerDto();
		player.setName(name);
		player.setPost(post);
		player.setAttackSkill(attSkill);
		player.setDefenseSkill(defSkill);
		player.setPrice(price);
		if (isInTeam) {
			player.setTeam(testTeamDto);
		}
		return player;
	}
}
