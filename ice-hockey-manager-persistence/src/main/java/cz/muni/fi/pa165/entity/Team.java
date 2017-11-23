package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.CompetitionCountry;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompetitionCountry competitionCountry;

    @OneToOne
    @JoinColumn(name = "humanPlayerId")
    private HumanPlayer humanPlayer;

    @OneToMany(mappedBy = "team")
    private Set<HockeyPlayer> hockeyPlayers = new HashSet<>();

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private BigDecimal budget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public void setHumanPlayer(HumanPlayer humanPlayer) {
        this.humanPlayer = humanPlayer;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Set<HockeyPlayer> getHockeyPlayers() {
        return hockeyPlayers;
    }

    public void setHockeyPlayers(Set<HockeyPlayer> hockeyPlayers) {
        this.hockeyPlayers = hockeyPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team other = (Team) o;

        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", competitionCountry=" + competitionCountry +
                ", humanPlayer=" + humanPlayer +
                ", budget=" + budget +
                '}';
    }
}
