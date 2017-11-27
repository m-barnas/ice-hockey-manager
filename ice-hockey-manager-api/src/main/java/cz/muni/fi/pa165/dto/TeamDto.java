package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.CompetitionCountry;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Lukáš Kotol
 */
public class TeamDto {

    private String name;

    private CompetitionCountry competitionCountry;

    private HumanPlayerDto humanPlayer;

    private Set<HockeyPlayerDto> hockeyPlayers = new HashSet<>();

    private BigDecimal budget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompetitionCountry getCompetitionCountry() {
        return competitionCountry;
    }

    public void setCompetitionCountry(CompetitionCountry competitionCountry) {
        this.competitionCountry = competitionCountry;
    }

    public HumanPlayerDto getHumanPlayer() {
        return humanPlayer;
    }

    public void setHumanPlayer(HumanPlayerDto humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public Set<HockeyPlayerDto> getHockeyPlayers() {
        return hockeyPlayers;
    }

    public void setHockeyPlayers(Set<HockeyPlayerDto> hockeyPlayers) {
        this.hockeyPlayers = hockeyPlayers;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamDto)) return false;

        TeamDto teamDTO = (TeamDto) o;

        return Objects.equals(getName(), teamDTO.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "TeamDto{" +
                "name='" + name + '\'' +
                ", competitionCountry=" + competitionCountry +
                ", humanPlayer=" + humanPlayer +
                ", hockeyPlayers=" + hockeyPlayers +
                ", budget=" + budget +
                '}';
    }
}