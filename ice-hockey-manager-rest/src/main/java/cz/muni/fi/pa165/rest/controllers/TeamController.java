package cz.muni.fi.pa165.rest.controllers;

import static cz.muni.fi.pa165.rest.ApiUri.*;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.dto.TeamSpendMoneyDto;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.facade.TeamFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static cz.muni.fi.pa165.rest.ApiUri.*;


/**
 * Created by Lukas Kotol on 12/13/2017.
 */
@RestController
@RequestMapping(path = ROOT_URI_TEAMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    @Autowired
    TeamFacade teamFacade;

    @Autowired
    HumanPlayerFacade humanPlayerFacade;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public TeamDto findById(@PathVariable("id") long id) {
        return teamFacade.getTeamById(id);
    }


    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TeamDto> findAll() {
        return teamFacade.getAllTeams();
    }


//    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
//    public void deleteById(@PathVariable("id") long id){
//        teamFacade.deleteTeam(id);
//    }

    @RequestMapping(path = "getByCompetitionCountry/{competitionCountry}", method = RequestMethod.GET)
    public List<TeamDto> getByCountry(@PathVariable("competitionCountry") CompetitionCountry competitionCountry) {
        return teamFacade.getTeamsByCountry(competitionCountry);
    }

    @RequestMapping(path = "/spendMoneyFromBudget", method = RequestMethod.POST)
    public TeamDto spendMoneyFromBudget(@RequestBody TeamSpendMoneyDto teamSpendMoneyDto) throws TeamServiceException {
        teamFacade.spendMoneyFromBudget(teamSpendMoneyDto.getId(), teamSpendMoneyDto.getAmount());
        return teamFacade.getTeamById(teamSpendMoneyDto.getId());
    }

    @RequestMapping(path = "/{id}/price", method = RequestMethod.GET)
    public BigDecimal getPrice(@PathVariable("id") long id) {
        return teamFacade.getTeamPrice(id);
    }

    @RequestMapping(path = "/{id}/attack", method = RequestMethod.GET)
    public int getAttack(@PathVariable("id") long id) {
        return teamFacade.getTeamAttackSkill(id);
    }

    @RequestMapping(path = "/{id}/defense", method = RequestMethod.GET)
    public int getDefense(@PathVariable("id") long id) {
        return teamFacade.getTeamDefenseSkill(id);
    }


}
