package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.TeamFacade;
import cz.muni.fi.pa165.service.HockeyPlayerService;
import cz.muni.fi.pa165.service.HumanPlayerService;
import cz.muni.fi.pa165.service.TeamService;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lukáš Kotol
 */

@Service
@Transactional
public class TeamFacadeImpl implements TeamFacade {


    @Autowired
    private TeamService teamService;

    @Autowired
    private HumanPlayerService humanPlayerService;

    @Autowired
    private HockeyPlayerService hockeyPlayerService;

    @Autowired
    private BeanMappingService beanMappingService;


    @Override
    public Long createTeam(TeamDto teamDto) {
        Team entityTeam = beanMappingService.mapTo(teamDto, Team.class);
        if (teamDto.getHumanPlayerId() != null) {
            HumanPlayer humanPlayer = humanPlayerService.findById(teamDto.getHumanPlayerId());
            if (humanPlayer == null) {
                throw new IllegalArgumentException();
            }
            entityTeam.setHumanPlayer(humanPlayer);
        }
        Team team = teamService.createTeam(entityTeam);
        teamDto.setBudget(team.getBudget());
        teamDto.setId(team.getId());
        return team.getId();
    }

    @Override
    public void deleteTeam(Long teamId) {
        teamService.deleteTeam(teamService.findById(teamId));
    }

    @Override
    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamService.findAll();
        List<TeamDto> teamDtos = beanMappingService.mapTo(teams, TeamDto.class);
        return teamDtos;
    }

    @Override
    public List<TeamDto> getTeamsByCountry(CompetitionCountry competitionCountry) {
        return beanMappingService.mapTo(teamService.findByCompetitionCountry(competitionCountry), TeamDto.class);
    }

    @Override
    public TeamDto getTeamById(Long id) {
        Team team = teamService.findById(id);
        if (team == null) {
            return null;
        }
        TeamDto teamDto = beanMappingService.mapTo(team, TeamDto.class);
        if (team.getHumanPlayer() != null) {
            teamDto.setHumanPlayerId(team.getHumanPlayer().getId());
        }
        return teamDto;
    }

    @Override
    public void spendMoneyFromBudget(Long teamId, BigDecimal amount) throws TeamServiceException {
        teamService.spendMoneyFromBudget(teamService.findById(teamId), amount);
    }

    @Override
    public BigDecimal getTeamPrice(Long teamId) {
        return teamService.getTeamPrice(teamService.findById(teamId));
    }

    @Override
    public int getTeamAttackSkill(Long teamId) {
        return teamService.getTeamAttackSkill(teamService.findById(teamId));
    }

    @Override
    public int getTeamDefenseSkill(Long teamId) {
        return teamService.getTeamDefenseSkill(teamService.findById(teamId));
    }

    @Override
    public TeamDto findTeamByName(String name) {
        Team team = teamService.findByName(name);
        if (team == null) {
            return null;
        }
        return beanMappingService.mapTo(team, TeamDto.class);
    }

    @Override
    public void addHockeyPlayer(Long teamId, Long hockeyPlayerId) {
        teamService.addHockeyPlayer(teamService.findById(teamId), hockeyPlayerService.findById(hockeyPlayerId));
    }

    @Override
    public void removeHockeyPlayer(Long teamId, Long hockeyPlayerId) {
        teamService.removeHockeyPlayer(teamService.findById(teamId), hockeyPlayerService.findById(hockeyPlayerId));
    }
}
