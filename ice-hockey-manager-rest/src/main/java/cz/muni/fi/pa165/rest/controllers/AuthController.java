package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.RegisterHumanPlayerDto;
import cz.muni.fi.pa165.exceptions.ManagerAuthenticationException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cz.muni.fi.pa165.rest.ApiUri.ROOT_URI;


/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@RestController
public class AuthController {

    @Autowired
    private HumanPlayerFacade humanPlayerFacade;

    @RequestMapping(path = ROOT_URI + "/register", method = RequestMethod.POST)
    public void registerHunter(@RequestBody RegisterHumanPlayerDto registerHumanPlayerDto)
            throws ManagerAuthenticationException {
        humanPlayerFacade.register(registerHumanPlayerDto, registerHumanPlayerDto.getPassword());
    }
}
