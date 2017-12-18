package cz.muni.fi.pa165.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.HockeyPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.rest.config.RestConfiguration;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static cz.muni.fi.pa165.rest.ApiUri.ROOT_URI_HOCKEY_PLAYERS;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@WebAppConfiguration
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HockeyPlayerControllerTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@InjectMocks
	private HockeyPlayerController hockeyPlayerController = new HockeyPlayerController();

	@Mock
	private HockeyPlayerFacade hockeyPlayerFacade;

	@Mock
	private TeamFacade teamFacade;

	//@Autowired
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

	private TeamDto kometa;
	private HockeyPlayerDto hasek;
	private HockeyPlayerDto kaberle;
	private HockeyPlayerDto zidlicky;
	private HockeyPlayerDto jagr;
	private HockeyPlayerDto elias;
	private HockeyPlayerDto hemsky;

	@BeforeClass
	public void setUp() {
		RestConfiguration restConfiguration = new RestConfiguration();
		mappingJackson2HttpMessageConverter = restConfiguration.customJackson2HttpMessageConverter();
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(hockeyPlayerController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@BeforeMethod
	public void prepareTestPlayers() {
		hasek = createPlayer(1L, "Dominik Hasek", Position.GOALKEEPER, 1, 95, BigDecimal.valueOf(90));
		kaberle = createPlayer(2L, "Tomas Kaberle", Position.DEFENSEMAN, 20, 80, BigDecimal.valueOf(50));
		zidlicky = createPlayer(3L, "Marek Zidlicky", Position.DEFENSEMAN, 25, 70, BigDecimal.valueOf(80));
		jagr = createPlayer(4L, "Jaromir Jagr", Position.RIGHT_WING, 99, 10, BigDecimal.valueOf(100));
		elias = createPlayer(5L, "Patrik Elias", Position.CENTER, 70, 65, BigDecimal.valueOf(70));
		hemsky = createPlayer(6L, "Ales Hemsky", Position.LEFT_WING, 80, 50, BigDecimal.valueOf(60));

		kometa = new TeamDto();
		kometa.setId(1L);
		kometa.setCompetitionCountry(CompetitionCountry.CZECH_REPUBLIC);
		kometa.setName("HC Kometa Brno");
		kometa.setHockeyPlayers(Stream.of(hasek, kaberle, jagr, elias).collect(Collectors.toSet()));
		hasek.setTeam(kometa);
		kaberle.setTeam(kometa);
		zidlicky.setTeam(null);
		jagr.setTeam(kometa);
		elias.setTeam(kometa);
		hemsky.setTeam(null);
	}

	@Test
	public void createHockeyPlayer() throws Exception {
		when(hockeyPlayerFacade.create(hasek)).thenReturn(1L);
		String hasekJson = convertObjectToJson(hasek);

		MvcResult result = mockMvc.perform(put(ROOT_URI_HOCKEY_PLAYERS + "/create")
				.contentType(MediaType.APPLICATION_JSON).content(hasekJson))
				.andExpect(status().isOk()).andReturn();
		String resultJson = result.getResponse().getContentAsString();
		assertThat(resultJson).isEqualTo(convertObjectToJson(hasek));
	}

	@Test
	public void deletePlayer() throws Exception {
		doNothing().when(hockeyPlayerFacade).delete(1L);
		mockMvc.perform(delete(ROOT_URI_HOCKEY_PLAYERS + "/1")).andExpect(status().isOk());
	}

	@Test
	public void getAllPlayers() throws Exception {
		doReturn(Stream.of(hasek, kaberle, zidlicky, jagr, elias, hemsky).collect(Collectors.toSet()))
				.when(hockeyPlayerFacade)
				.findAll();

		mockMvc.perform(get(ROOT_URI_HOCKEY_PLAYERS + "/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Dominik Hasek"))
				.andExpect(jsonPath("$.[?(@.id==2)].name").value("Tomas Kaberle"))
				.andExpect(jsonPath("$.[?(@.id==2)].price").value(50));

	}

	@Test
	public void getPlayersByTeam() throws  Exception {
		doReturn(kometa).when(teamFacade).getTeamById(1L);
		doReturn(Stream.of(hasek, kaberle, jagr, elias).collect(Collectors.toSet()))
				.when(hockeyPlayerFacade)
				.findByTeam(kometa);

		mockMvc.perform(get(ROOT_URI_HOCKEY_PLAYERS + "/getByTeam/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[?(@.id==1)].name").value("Dominik Hasek"))
				.andExpect(jsonPath("$.[?(@.id==3)]").doesNotExist());
	}

	@Test
	public void getFreeAgents() throws Exception {
		doReturn(Stream.of(zidlicky, hemsky).collect(Collectors.toSet()))
				.when(hockeyPlayerFacade)
				.findFreeAgents();

		mockMvc.perform(get(ROOT_URI_HOCKEY_PLAYERS + "/getFreeAgents"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$").isNotEmpty())
				.andExpect(jsonPath("$.[?(@.id==3)].name").value("Marek Zidlicky"))
				.andExpect(jsonPath("$.[?(@.id==3)].team").doesNotExist())
				.andExpect(jsonPath("$.[?(@.id==6)].name").value("Ales Hemsky"))
				.andExpect(jsonPath("$.[?(@.id==6)].team").doesNotExist())
				.andExpect(jsonPath("$.[?(@.id==1)]").doesNotExist())
				.andExpect(jsonPath("$.[?(@.id==2)]").doesNotExist())
				.andExpect(jsonPath("$.[?(@.id==4)]").doesNotExist())
				.andExpect(jsonPath("$.[?(@.id==5)]").doesNotExist());
	}

	@Test
	public void getByName() throws Exception {
		doReturn(jagr).when(hockeyPlayerFacade).findByName("Jaromir Jagr");

		mockMvc.perform(get(ROOT_URI_HOCKEY_PLAYERS + "/getByName/Jaromir Jagr"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[?(@.id==4)]").exists())
				.andExpect(jsonPath("$.[?(@.id==4)].name").value("Jaromir Jagr"))
				.andExpect(jsonPath("$.[?(@.id==4)].attackSkill").value(99));
	}

	private HockeyPlayerDto createPlayer(Long id, String name, Position post, int attSkill, int defSkill, BigDecimal price) {
		HockeyPlayerDto player = new HockeyPlayerDto();
		player.setId(id);
		player.setName(name);
		player.setPost(post);
		player.setAttackSkill(attSkill);
		player.setDefenseSkill(defSkill);
		player.setPrice(price);
		return player;
	}

	private String convertObjectToJson(Object object) throws Exception {
		ObjectMapper mapper = mappingJackson2HttpMessageConverter.getObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
