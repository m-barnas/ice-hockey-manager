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

    private TeamDTO firstTeamDto;

    private TeamDTO secondTeamDto;

    private LocalDateTime startTime;

    private Integer firstTeamScore;

    private Integer secondTeamScore;

    private GameState gameState;
}
