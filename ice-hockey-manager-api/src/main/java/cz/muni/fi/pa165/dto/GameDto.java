package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.GameState;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO for game.
 *
 * @author Marketa Elederova
 */
public class GameDto {

    private Long id;

    private TeamDto firstTeamDto;

    private TeamDto secondTeamDto;

    private LocalDateTime startTime;

    private Integer firstTeamScore;

    private Integer secondTeamScore;

    private GameState gameState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamDto getFirstTeamDto() {
        return firstTeamDto;
    }

    public void setFirstTeamDto(TeamDto firstTeamDto) {
        this.firstTeamDto = firstTeamDto;
    }

    public TeamDto getSecondTeamDto() {
        return secondTeamDto;
    }

    public void setSecondTeamDto(TeamDto secondTeamDto) {
        this.secondTeamDto = secondTeamDto;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getFirstTeamScore() {
        return firstTeamScore;
    }

    public void setFirstTeamScore(Integer firstTeamScore) {
        this.firstTeamScore = firstTeamScore;
    }

    public Integer getSecondTeamScore() {
        return secondTeamScore;
    }

    public void setSecondTeamScore(Integer secondTeamScore) {
        this.secondTeamScore = secondTeamScore;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.firstTeamDto);
        hash = 37 * hash + Objects.hashCode(this.secondTeamDto);
        hash = 37 * hash + Objects.hashCode(this.startTime);
        hash = 37 * hash + Objects.hashCode(this.firstTeamScore);
        hash = 37 * hash + Objects.hashCode(this.secondTeamScore);
        hash = 37 * hash + Objects.hashCode(this.gameState);
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
        if (!(obj instanceof GameDto)) {
            return false;
        }
        final GameDto other = (GameDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.firstTeamDto, other.firstTeamDto)) {
            return false;
        }
        if (!Objects.equals(this.secondTeamDto, other.secondTeamDto)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.firstTeamScore, other.firstTeamScore)) {
            return false;
        }
        if (!Objects.equals(this.secondTeamScore, other.secondTeamScore)) {
            return false;
        }
        if (this.gameState != other.gameState) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "firstTeamDto=" + firstTeamDto +
                ", secondTeamDto=" + secondTeamDto +
                ", startTime=" + startTime +
                ", firstTeamScore=" + firstTeamScore +
                ", secondTeamScore=" + secondTeamScore +
                ", gameState=" + gameState +
                '}';
    }
}
