package cz.muni.fi.pa165.facades;

import cz.muni.fi.pa165.dto.TeamCreateDTO;

/**
 * @author Lukáš Kotol
 */
public interface TeamFacade {
    public Long createTeam(TeamCreateDTO teamCreateDTO);
    public void addHockyPlayer(Long teamId, Long hockeyPlayerId);
    public void removeHockeyPlayer(Long teamId, Long hockeyPlayerId);
    public void deleteTeam(Long teamId);
}
