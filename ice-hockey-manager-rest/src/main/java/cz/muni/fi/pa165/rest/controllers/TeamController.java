package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.TeamAddRemovePlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.dto.TeamSpendMoneyDto;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static cz.muni.fi.pa165.rest.ApiUri.ROOT_URI_TEAMS;


/**
 * Created by Lukas Kotol on 12/13/2017.
 */
@RestController
@RequestMapping(path = ROOT_URI_TEAMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    final static Logger log = LoggerFactory.getLogger(TeamController.class);


    @Autowired
    TeamFacade teamFacade;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public TeamDto findById(@PathVariable("id") long id) {
        log.debug("findById({})", id);
        return teamFacade.getTeamById(id);
    }

    @RequestMapping(path = "/getByName/{name}", method = RequestMethod.GET)
    public TeamDto findByName(@PathVariable("name") String name) {
        log.debug("findByName({})", name);
        return teamFacade.findTeamByName(name);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TeamDto> findAll() {
        log.debug("findAll");
        return teamFacade.getAllTeams();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") long id) {
        teamFacade.deleteTeam(id);
        log.debug("deleteById({})", id);
    }

    @RequestMapping(path = "getByCompetitionCountry/{competitionCountry}", method = RequestMethod.GET)
    public List<TeamDto> getByCountry(@PathVariable("competitionCountry") CompetitionCountry competitionCountry) {
        log.debug("getByCountry({})", competitionCountry);
        return teamFacade.getTeamsByCountry(competitionCountry);
    }

    @RequestMapping(path = "/spendMoneyFromBudget", method = RequestMethod.POST)
    public void spendMoneyFromBudget(@RequestBody TeamSpendMoneyDto teamSpendMoneyDto) throws TeamServiceException {
        teamFacade.spendMoneyFromBudget(teamSpendMoneyDto.getTeamId(), teamSpendMoneyDto.getAmount());
        log.debug("spendMoneyFromBudget({}, {})", teamSpendMoneyDto.getTeamId(), teamSpendMoneyDto.getAmount());
    }

    @RequestMapping(path = "/{id}/price", method = RequestMethod.GET)
    public BigDecimal getPrice(@PathVariable("id") long id) {
        log.debug("getPrice({})", id);
        return teamFacade.getTeamPrice(id);
    }

    @RequestMapping(path = "/{id}/attack", method = RequestMethod.GET)
    public int getAttack(@PathVariable("id") long id) {
        log.debug("getAttack({})", id);
        return teamFacade.getTeamAttackSkill(id);
    }

    @RequestMapping(path = "/{id}/defense", method = RequestMethod.GET)
    public int getDefense(@PathVariable("id") long id) {
        log.debug("getDefense({})", id);
        return teamFacade.getTeamDefenseSkill(id);
    }

    @RequestMapping(path = "/create", method = RequestMethod.PUT)
    public void create(@RequestBody TeamDto teamDto) {
        teamFacade.createTeam(teamDto);
        log.debug("create(createdId = {})", teamDto.getId());
    }

    @RequestMapping(path = "/addHockeyPlayer", method = RequestMethod.POST)
    public void addHockeyPlayer(@RequestBody TeamAddRemovePlayerDto teamAddRemovePlayerDto) {
        teamFacade.addHockeyPlayer(teamAddRemovePlayerDto.getTeamId(), teamAddRemovePlayerDto.getHockeyPlayerId());
        log.debug("addHockeyPlayer(teamId = {}, hockeyPlayerId = {})", teamAddRemovePlayerDto.getTeamId(), teamAddRemovePlayerDto.getHockeyPlayerId());
    }

    @RequestMapping(path = "/removeHockeyPlayer", method = RequestMethod.POST)
    public void removeHockeyPlayer(@RequestBody TeamAddRemovePlayerDto teamAddRemovePlayerDto) {
        teamFacade.removeHockeyPlayer(teamAddRemovePlayerDto.getTeamId(), teamAddRemovePlayerDto.getHockeyPlayerId());
        log.debug("removeHockeyPlayer(teamId = {}, hockeyPlayerId = {})", teamAddRemovePlayerDto.getTeamId(), teamAddRemovePlayerDto.getHockeyPlayerId());
    }
}
