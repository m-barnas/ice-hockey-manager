package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.TeamCreateDTO;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.enums.CompetitionCountry;

import java.util.List;

/**
 * @author Lukáš Kotol
 */
public class TeamFacadeImpl implements TeamFacade {
    @Override
    public Long createTeam(TeamCreateDTO teamCreateDTO) {
        return null;
    }

    @Override
    public void addHockyPlayer(Long teamId, Long hockeyPlayerId) {

    }

    @Override
    public void removeHockeyPlayer(Long teamId, Long hockeyPlayerId) {

    }

    @Override
    public void deleteTeam(Long teamId) {

    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return null;
    }

    @Override
    public List<TeamDTO> getTeamsByCountry(CompetitionCountry competitionCountry) {
        return null;
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        return null;
    }
}
