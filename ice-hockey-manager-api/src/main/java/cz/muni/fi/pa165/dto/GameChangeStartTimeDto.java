package cz.muni.fi.pa165.dto;

import java.time.LocalDateTime;
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
}
