package cz.muni.fi.pa165.rest.controllers;

import static cz.muni.fi.pa165.rest.ApiUri.*;

import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.facade.TeamFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by Lukas Kotol on 12/13/2017.
 */
@RestController
@RequestMapping(path = ROOT_URI_TEAMS, produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    @Autowired
    TeamFacade teamFacade;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public TeamDto findById(@PathVariable("id") long id) {
        return teamFacade.getTeamById(id);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TeamDto> findAll(){
        return teamFacade.getAllTeams();
    }
}
