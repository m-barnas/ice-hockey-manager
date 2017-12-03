package cz.muni.fi.pa165.rest.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.muni.fi.pa165.rest.ApiUri.*;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@RestController
@RequestMapping(path = ROOT_URI_HOME, produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController {
}
