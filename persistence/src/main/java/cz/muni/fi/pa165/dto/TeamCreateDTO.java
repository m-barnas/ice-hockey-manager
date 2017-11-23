package cz.muni.fi.pa165.dto;


import cz.muni.fi.pa165.enums.CompetitionCountry;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class TeamCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private CompetitionCountry competitionCountry;

    @NotNull
    private Long humanPlayerId;

    @NotNull
    @Min(0)
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

    public Long getHumanPlayerId() {
        return humanPlayerId;
    }

    public void setHumanPlayerId(Long humanPlayerId) {
        this.humanPlayerId = humanPlayerId;
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
        if (!(o instanceof TeamCreateDTO)) return false;

        TeamCreateDTO that = (TeamCreateDTO) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "TeamCreateDTO{" +
                "name='" + name + '\'' +
                ", competitionCountry=" + competitionCountry +
                ", humanPlayerId=" + humanPlayerId +
                ", budget=" + budget +
                '}';
    }
}