package cz.muni.fi.pa165.service.mappers;

import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.Team;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bean mapping service implementation.
 *
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return dozer.map(u, mapToClass);
    }

    @Override
    public TeamDto mapTo(Team u, Class<TeamDto> teamDtoClass) {
        TeamDto teamDto = dozer.map(u, teamDtoClass);
        if (u.getHumanPlayer() != null) {
            teamDto.setHumanPlayerId(u.getHumanPlayer().getId());
        }
        return teamDto;
    }

    @Override
    public List<TeamDto> mapTo(List<Team> teams, Class<TeamDto> teamDtoClass) {
        List<TeamDto> mappedCollection = new ArrayList<>();
        for (Team object : teams) {
            TeamDto teamDto = dozer.map(object, teamDtoClass);
            if (object.getHumanPlayer() != null) {
                teamDto.setHumanPlayerId(object.getHumanPlayer().getId());
            }
            mappedCollection.add(teamDto);
        }
        return mappedCollection;
    }

    @Override
    public Mapper getMapper() {
        return dozer;
    }
}
