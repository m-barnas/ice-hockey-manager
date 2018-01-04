package cz.muni.fi.pa165.service.mappers;

import cz.muni.fi.pa165.dao.TeamDao;
import cz.muni.fi.pa165.dto.GameDto;
import cz.muni.fi.pa165.dto.RegisterHumanPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.Game;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Bean mapping service.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
public interface BeanMappingService {

    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public <T> T mapTo(Object u, Class<T> mapToClass);

    public TeamDto mapTo(Team u, Class<TeamDto> teamDtoClass);

    public List<TeamDto> mapTo(List<Team> teams, Class<TeamDto> teamDtoClass);

    public GameDto mapToGame(Game u, Class<GameDto> gameDtoClass);

    public List<GameDto> mapToGame(List<Game> games, Class<GameDto> gameDtoClass);

    public Mapper getMapper();
}
