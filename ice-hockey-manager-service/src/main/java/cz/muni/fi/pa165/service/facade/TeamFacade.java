package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.TeamCreateDTO;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.enums.CompetitionCountry;

import java.util.List;

/**
 * @author Lukáš Kotol
 */
public interface TeamFacade {
    public Long createTeam(TeamCreateDTO teamCreateDTO);
    public void addHockyPlayer(Long teamId, Long hockeyPlayerId);
    public void removeHockeyPlayer(Long teamId, Long hockeyPlayerId);
    public void deleteTeam(Long teamId);
    public List<TeamDTO> getAllTeams();
    public List<TeamDTO> getTeamsByCountry(CompetitionCountry competitionCountry);
    public TeamDTO getTeamById(Long id);
}
