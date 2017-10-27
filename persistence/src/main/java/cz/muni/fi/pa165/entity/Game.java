package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.GameState;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Marketa Elederova
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne
    private Team firstTeam;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne
    private Team secondTeam;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = true)
    private Integer firstTeamScore;

    @Column(nullable = true)
    private Integer secondTeamScore;

    @NotNull
    @Column(nullable = false, columnDefinition="default 'OK'")
    @Enumerated(EnumType.STRING)
    private GameState gameState = GameState.OK;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
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
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.firstTeam);
        hash = 59 * hash + Objects.hashCode(this.secondTeam);
        hash = 59 * hash + Objects.hashCode(this.startTime);
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
        if (!(obj instanceof Game)) {
            return false;
        }
        final Game other = (Game) obj;
        if (!Objects.equals(this.firstTeam, other.firstTeam)) {
            return false;
        }
        if (!Objects.equals(this.secondTeam, other.secondTeam)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        return true;
    }

}
