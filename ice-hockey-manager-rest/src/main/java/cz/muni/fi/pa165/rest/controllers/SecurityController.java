package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.exceptions.AuthenticationException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@RestController
public class SecurityController {

    @Autowired
    private HumanPlayerFacade humanPlayerFacade;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public void registerHunter(@RequestBody HumanPlayerDto humanPlayerDto, String unecryptedPassword)
            throws AuthenticationException {
//        ResourceAccess.verify(registrateHunterDTO);
        humanPlayerFacade.register(humanPlayerDto, unecryptedPassword);
    }
}
