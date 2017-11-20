package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Lukas Kotol on 17.11.17.
 */

@Service
public interface TeamService {
    public Team findById(Long id);
    public List<Team> findAll();
    public List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry);
    public Team findByName(String name);
    public Team createTeam(Team team);
    public void deleteTeam(Team team);
    public void addHockeyPlayer(Team team, HockeyPlayer hockeyPlayer);
    public void removeHockeyPlayer(Team team, HockeyPlayer hockeyPlayer);
    public void spendMoneyFromBudget(Team team, BigDecimal amount) throws TeamServiceException;
    public BigDecimal getTeamPrice(Team team);
    public int getTeamAttackSkill(Team team);
    public int getTeamDefenseSkill(Team team);
}
