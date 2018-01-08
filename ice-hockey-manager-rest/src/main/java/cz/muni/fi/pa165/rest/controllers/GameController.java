package cz.muni.fi.pa165.rest.controllers;

import cz.muni.fi.pa165.dto.GameChangeStartTimeDto;
import cz.muni.fi.pa165.dto.GameCreateDto;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.facade.GameFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.muni.fi.pa165.rest.ApiUri.*;
import cz.muni.fi.pa165.rest.exceptions.InternalServerErrorException;
import cz.muni.fi.pa165.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.rest.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST controller for games.
 *
 * @author Marketa Elederova
 */
@RestController
@RequestMapping(path = ROOT_URI_GAMES, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    @Autowired
    GameFacade gameFacade;

    /**
     * Create a new game.
     *
     * @param gameCreateDto {@link GameCreateDto} with required fields for creation
     * @return created {@link GameDto}
     * @throws InvalidParameterException if game is invalid
     * @throws InternalServerErrorException if some other error occurs
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final GameDto createGame(@RequestBody GameCreateDto gameCreateDto)
            throws InvalidParameterException, InternalServerErrorException {
        try {
            return gameFacade.create(gameCreateDto);
        } catch (IllegalArgumentException ex) {
            throw new InvalidParameterException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Delete game.
     *
     * @param id of deleted game
     * @throws ResourceNotFoundException if game do not exists
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public final void deleteGame(@PathVariable("id") long id)
            throws ResourceNotFoundException, InternalServerErrorException {
        try {
            gameFacade.delete(id);
        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Set game state to CANCELED.
     *
     * @param id of game to be canceled
     * @throws InvalidParameterException if game with given id does not exist
     * or it has been already played
     * @throws InternalServerErrorException if some other error occurs
     */
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.PUT)
    public final void cancelGame(@PathVariable("id") long id)
            throws InvalidParameterException, InternalServerErrorException {
        try {
            gameFacade.cancel(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidParameterException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Set game state to OK.
     *
     * @param id of game to be retrieved
     * @throws InvalidParameterException if game with given id does not exist
     * @throws InternalServerErrorException if some error occurs
     */
    @RequestMapping(value = "/retrieve/{id}", method = RequestMethod.PUT)
    public final void retrieveGame(@PathVariable("id") long id)
            throws InvalidParameterException, InternalServerErrorException {
        try {
            gameFacade.retrieve(id);
        } catch(IllegalArgumentException ex) {
            throw new InvalidParameterException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Change game start time.
     *
     * @param id of game to be changed
     * @param gameChangeStartTimeDto with information about new start time
     * @return updated {@link GameDto}
     * @throws InvalidParameterException if game with given id does not exist
     * or it has been already played
     * @throws InternalServerErrorException if some other error occurs
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final GameDto changeStartTime(@PathVariable("id") long id,
            @RequestBody GameChangeStartTimeDto gameChangeStartTimeDto)
            throws InvalidParameterException, InternalServerErrorException {
        gameChangeStartTimeDto.setId(id);
        try {
            return gameFacade.changeStartTime(gameChangeStartTimeDto);
        } catch(IllegalArgumentException ex) {
            throw new InvalidParameterException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Get game with given id.
     *
     * @param id of game to be found
     * @return found {@link GameDto}
     * @throws ResourceNotFoundException if game with given id does not exists
     * @throws InternalServerErrorException if some other error occurs
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDto findById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        GameDto foundGame;
        try {
            foundGame = gameFacade.findById(id);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
        if (foundGame == null) {
            throw new ResourceNotFoundException();
        }
        return foundGame;
    }

    /**
     * Get all games of given team.
     *
     * @param teamId id of the team
     * @return list of {@link GameDto}s
     * @throws ResourceNotFoundException if team with given id does not exists
     * @throws InternalServerErrorException if some error occurs
     */
    @RequestMapping(path = "/byteam", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> findByTeam(@RequestParam("teamId") Long teamId)
            throws ResourceNotFoundException, InternalServerErrorException {
        try {
            return gameFacade.findByTeam(teamId);
        } catch(IllegalArgumentException ex) {
            throw new ResourceNotFoundException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Get all games.
     *
     * @return list of {@link GameDto}s
     * @throws InternalServerErrorException if some error occurs
     */
    @RequestMapping(path = "/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> findAll()
            throws InternalServerErrorException {
        try {
            return gameFacade.findAll();
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Get scheduled games - games with state OK which were not played yet.
     *
     * @return list of {@link GameDto}s
     * @throws InternalServerErrorException if some error occurs
     */
    @RequestMapping(path = "/scheduled", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GameDto> findScheduledGames()
            throws InternalServerErrorException {
        try {
            return gameFacade.findScheduledGames();
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Play all not played hockey games in the past.
     *
     * @return number of just played games
     * @throws InternalServerErrorException if some error occurs
     */
    @RequestMapping(value = "/play", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final int playGames()
            throws InternalServerErrorException {
        List<GameDto> playedGames;
        try {
            playedGames = gameFacade.playGames();
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
        return playedGames.size();
    }
}
