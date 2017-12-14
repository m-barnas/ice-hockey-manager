package cz.muni.fi.pa165.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.firstTeamId);
        hash = 67 * hash + Objects.hashCode(this.secondTeamId);
        hash = 67 * hash + Objects.hashCode(this.startTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GameCreateDto)) {
            return false;
        }
        final GameCreateDto other = (GameCreateDto) obj;
        if (!Objects.equals(this.firstTeamId, other.firstTeamId)) {
            return false;
        }
        if (!Objects.equals(this.secondTeamId, other.secondTeamId)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "firstTeamId=" + firstTeamId +
                ", secondTeamId=" + secondTeamId +
                ", startTime=" + startTime +
                '}';
    }
}
