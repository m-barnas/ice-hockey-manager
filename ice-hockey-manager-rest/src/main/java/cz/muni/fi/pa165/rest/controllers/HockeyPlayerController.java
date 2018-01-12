package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.HockeyPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.rest.ApiUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

import static cz.muni.fi.pa165.rest.ApiUri.*;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
@RestController
@RequestMapping(path = ROOT_URI_HOCKEY_PLAYERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class HockeyPlayerController {

	@Autowired
	HockeyPlayerFacade hockeyPlayerFacade;

	@Autowired
	TeamFacade teamFacade;

	final static Logger log = LoggerFactory.getLogger(HockeyPlayerController.class);

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public HockeyPlayerDto findById(@PathVariable("id") long id) {
		log.debug("findById({})", id);
		return hockeyPlayerFacade.findById(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") long id) {
		log.debug("deleteById({})", id);
		hockeyPlayerFacade.delete(id);
	}

	@RequestMapping(path = ApiUri.SubApiUri.CREATE, method = RequestMethod.PUT)
	public HockeyPlayerDto create(@RequestBody HockeyPlayerDto hockeyPlayerDto) {
		hockeyPlayerFacade.create(hockeyPlayerDto);
		log.debug("create(createdId = {})", hockeyPlayerDto.getId());
		return hockeyPlayerDto;
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findAll() {
		log.debug("findAll");
		return hockeyPlayerFacade.findAll();
	}

	@RequestMapping(path = "/get-by-name/{name}", method = RequestMethod.GET)
	public HockeyPlayerDto findByName(@PathVariable("name") String name) {
		log.debug("findByName({})", name);
		return hockeyPlayerFacade.findByName(name);
	}

	@RequestMapping(path = "/get-by-team/{teamId}", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByTeam(@PathVariable("teamId") Long teamId) {
		log.debug("findByTeam({})", teamId);
		return hockeyPlayerFacade.findByTeam(teamFacade.getTeamById(teamId));
	}

	@RequestMapping(path = "/get-free-agents", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findFreeAgents() {
		log.debug("findFreeAgents");
		return hockeyPlayerFacade.findFreeAgents();
	}

	@RequestMapping(path = "/get-by-att-skill/{attSkill}", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByAttSkill(@PathVariable("attSkill") int attSkill) {
		log.debug("findByAttackSkill({})", attSkill);
		return hockeyPlayerFacade.findByAttSkill(attSkill);
	}

	@RequestMapping(path = "/get-by-def-skill/{defSkill}", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByDefSkill(@PathVariable("defSkill") int defSkill) {
		log.debug("findByDefenseSkill({})", defSkill);
		return hockeyPlayerFacade.findByDefSkill(defSkill);
	}

	@RequestMapping(path = "/get-by-post/{post}", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByPost(@PathVariable("post") Position post) {
		log.debug("findByPost({})", post);
		return hockeyPlayerFacade.findByPost(post);
	}

	@RequestMapping(path = "/get-by-price/{price}", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByPrice(@PathVariable("price") BigDecimal price) {
		log.debug("findByPrice({})", price);
		return hockeyPlayerFacade.findByPriceLessOrEqualThan(price);
	}
}
