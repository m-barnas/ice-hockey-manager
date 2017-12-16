package cz.muni.fi.pa165.dto;

/**
 * Created by Lukas Kotol on 12/16/2017.
 */
public class TeamAddRemovePlayerDto {
    private Long teamId;
    private Long hockeyPlayerId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getHockeyPlayerId() {
        return hockeyPlayerId;
    }

    public void setHockeyPlayerId(Long hockeyPlayerId) {
        this.hockeyPlayerId = hockeyPlayerId;
    }
}
