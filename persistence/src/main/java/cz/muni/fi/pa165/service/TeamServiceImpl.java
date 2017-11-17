package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.TeamDao;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Lukas Kotol on 17.11.17.
 */
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Override
    public Team findById(Long id) {
        return teamDao.findById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamDao.findAll();
    }

    @Override
    public List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry) {
        return teamDao.findByCompetitionCountry(competitionCountry);
    }

    @Override
    public Team findByName(String name) {
        return teamDao.findByName(name);
    }

    @Override
    public Team createTeam(Team team) {
        teamDao.create(team);
        return team;
    }

    @Override
    public void deleteTeam(Team team) {
        teamDao.delete(team);
    }

    @Override
    public void addHockeyPlayer(Team team, HockeyPlayer hockeyPlayer) {
        team.addHockeyPlayer(hockeyPlayer);
    }

    @Override
    public void removeHockeyPlayer(Team team, HockeyPlayer hockeyPlayer) {
        team.removeHockeyPlayer(hockeyPlayer);
    }

    @Override
    public void spendMoneyFromBudget(Team team, BigDecimal amount) {
        BigDecimal resultBudget = team.getBudget().subtract(amount);
        team.setBudget(resultBudget);
    }

    @Override
    public BigDecimal getTeamPrice(Team team) {
        return team.getHockeyPlayers().stream().map(HockeyPlayer::getPrice).reduce(BigDecimal::add).get();
    }

    @Override
    public int getTeamAttackSkill(Team team) {
        return team.getHockeyPlayers().stream().mapToInt(HockeyPlayer::getAttackSkill).sum();
    }

    @Override
    public int getTeamDefenseSkill(Team team) {
        return team.getHockeyPlayers().stream().mapToInt(HockeyPlayer::getDefenseSkill).sum();
    }
}
