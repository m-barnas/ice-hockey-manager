package cz.muni.fi.pa165.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * DTO for changing game start time.
 *
 * @author Marketa Elederova
 */
public class GameChangeStartTimeDto {

    private Long id;

    @NotNull
    private LocalDateTime startTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.startTime);
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
        if (!(obj instanceof GameChangeStartTimeDto)) {
            return false;
        }
        final GameChangeStartTimeDto other = (GameChangeStartTimeDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        return true;
    }
}
