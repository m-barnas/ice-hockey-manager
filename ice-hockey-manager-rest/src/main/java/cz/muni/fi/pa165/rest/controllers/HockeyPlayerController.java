package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.enums.Position;
import cz.muni.fi.pa165.facade.HockeyPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
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

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public HockeyPlayerDto findById(@PathVariable("id") long id) {
		return hockeyPlayerFacade.findById(id);
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findAll() {
		return hockeyPlayerFacade.findAll();
	}

	@RequestMapping(path = "/byname", method = RequestMethod.GET)
	public HockeyPlayerDto findByName(@RequestParam("name") String name) {
		return hockeyPlayerFacade.findByName(name);
	}

	@RequestMapping(path = "/byattskill", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByAttSkill(@RequestParam("attSkill") int attSkill) {
		return hockeyPlayerFacade.findByAttSkill(attSkill);
	}

	@RequestMapping(path = "/bydefskill", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByDefSkill(@RequestParam("defSkill") int defSkill) {
		return hockeyPlayerFacade.findByDefSkill(defSkill);
	}

	@RequestMapping(path = "/bypost", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByPost(@RequestParam("post") Position post) {
		return hockeyPlayerFacade.findByPost(post);
	}

	@RequestMapping(path = "/byprice", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByPrice(@RequestParam("price") BigDecimal price) {
		return hockeyPlayerFacade.findByPriceLessOrEqualThan(price);
	}

	@RequestMapping(path = "/bypostandprice", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByPostAndPrice(
			@RequestParam("post") Position post, @RequestParam("price") BigDecimal price) {
		return hockeyPlayerFacade.findByPostAndPrice(post, price);
	}

	@RequestMapping(path = "/byteam", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findByTeam(@RequestParam("teamName") String teamName) {
		return hockeyPlayerFacade.findByTeam(teamFacade.findTeamByName(teamName));
	}

	@RequestMapping(path = "/freeagents", method = RequestMethod.GET)
	public Collection<HockeyPlayerDto> findFreeAgents() {
		return hockeyPlayerFacade.findFreeAgents();
	}
}
