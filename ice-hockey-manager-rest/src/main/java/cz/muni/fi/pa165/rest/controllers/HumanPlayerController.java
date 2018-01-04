package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.rest.ApiUri;
import cz.muni.fi.pa165.rest.utils.ResourceAccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public HumanPlayerDto findById(@PathVariable long id) {
        return humanPlayerFacade.findById(id);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<HumanPlayerDto> findAll() {
        return humanPlayerFacade.findAll();
    }

    @RequestMapping(path = "/byemail", method = RequestMethod.GET)
    public HumanPlayerDto findByEmail(@RequestParam String email) {
        return humanPlayerFacade.findByEmail(email);
    }

    @RequestMapping(path = "/byusername", method = RequestMethod.GET)
    public HumanPlayerDto findByUsername(@RequestParam String username) {
        return humanPlayerFacade.findByUsername(username);
    }

    // only authenticated user
    @RequestMapping(path = SubApiUri.CHANGE_PASSWD, method = RequestMethod.POST)
    public HumanPlayerDto update(@AuthenticationPrincipal HumanPlayerDto loggedHumanPlayer, @RequestParam Long id,
                                 @RequestParam String oldPass, @RequestParam String newPass) {
        // only own password can change
        ResourceAccessUtils.verifyLoggedHumanPlayer(loggedHumanPlayer, id);
        return humanPlayerFacade.changePassword(id, oldPass, newPass);
    }

    // only authenticated user with role ADMIN
    @RequestMapping(path = ApiUri.SubApiUri.UPDATE, method = RequestMethod.POST)
    public HumanPlayerDto update(@RequestParam Long id, @RequestParam Role role) {
        return humanPlayerFacade.changeRole(id, role);
    }

    // only authenticated user with role ADMIN
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        humanPlayerFacade.delete(id);
    }

}
