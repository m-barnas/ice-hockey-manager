package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.TeamCreateDTO;
import cz.muni.fi.pa165.dto.TeamDTO;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;
import cz.muni.fi.pa165.facade.TeamFacade;
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
    private BeanMappingService beanMappingService;


    @Override
    public Long createTeam(TeamCreateDTO teamCreateDTO) {
        Team mappedTeam = beanMappingService.mapTo(teamCreateDTO, Team.class);
        Team newTeam = teamService.createTeam(mappedTeam);
        return newTeam.getId();
    }

    @Override
    public void addHockyPlayer(Long teamId, Long hockeyPlayerId) {
//        teamService.addHockeyPlayer(teamService.findById(teamId), hockeyPlayerService.findById(hockeyPlayerId));
    }

    @Override
    public void removeHockeyPlayer(Long teamId, Long hockeyPlayerId) {

    }

    @Override
    public void deleteTeam(Long teamId) {
        teamService.deleteTeam(teamService.findById(teamId));
    }

    @Override
    public List<TeamDTO> getAllTeams() {
        return beanMappingService.mapTo(teamService.findAll(), TeamDTO.class);
    }

    @Override
    public List<TeamDTO> getTeamsByCountry(CompetitionCountry competitionCountry) {
        return beanMappingService.mapTo(teamService.findByCompetitionCountry(competitionCountry), TeamDTO.class);
    }

    @Override
    public TeamDTO getTeamById(Long id) {
        Team team = teamService.findById(id);
        return (team == null) ? null : beanMappingService.mapTo(team, TeamDTO.class);
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
    public TeamDTO findTeamByName(String name) {
        return beanMappingService.mapTo(teamService.findByName(name), TeamDTO.class);
    }
}
