package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO for just created game.
 *
 * @author Marketa Elederova
 */
public class GameCreateDto {

    @NotNull
    private Long firstTeamId;

    @NotNull
    private Long secondTeamId;

    @NotNull
    private LocalDateTime startTime;

    public Long getFirstTeamId() {
        return firstTeamId;
    }

    public void setFirstTeamId(Long firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    public Long getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(Long secondTeamId) {
        this.secondTeamId = secondTeamId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
