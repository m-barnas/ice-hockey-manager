package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.rest.ApiUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static cz.muni.fi.pa165.rest.ApiUri.*;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@RestController
@RequestMapping(path = ROOT_URI_MANAGERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class HumanPlayerController {

    @Autowired
    HumanPlayerFacade humanPlayerFacade;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public HumanPlayerDto findById(@PathVariable("id") long id) {
        return humanPlayerFacade.findById(id);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<HumanPlayerDto> findAll() {
        return humanPlayerFacade.findAll();
    }

    @RequestMapping(path = "/byemail", method = RequestMethod.GET)
    public HumanPlayerDto findByEmail(@RequestParam("email") String email) {
        return humanPlayerFacade.findByEmail(email);
    }

    @RequestMapping(path = "/byusername", method = RequestMethod.GET)
    public HumanPlayerDto findByUsername(@RequestParam("username") String username) {
        return humanPlayerFacade.findByUsername(username);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        humanPlayerFacade.delete(id);
    }

    @RequestMapping(path = ApiUri.SubApiUri.UPDATE, method = RequestMethod.POST)
    public HumanPlayerDto update(@RequestParam Long id, @RequestParam String oldPasswd, @RequestParam String newPasswd) {
        return humanPlayerFacade.changePassword(id, oldPasswd, newPasswd);
    }

}
