package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.GameState;
import java.time.LocalDateTime;

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
}
