package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.HockeyPlayerDto;
import cz.muni.fi.pa165.dto.TeamCreateDto;
import cz.muni.fi.pa165.dto.TeamDto;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.exceptions.TeamServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lukáš Kotol
 */
public interface TeamFacade {
    /**
     * Created new Team.
     *
     * @param teamCreateDTO team which will be created.
     * @return id of created team.
     */
    Long createTeam(TeamCreateDto teamCreateDTO);

    /**
     * Removes team.
     *
     * @param teamId id of team which will be removed.
     */
    void deleteTeam(Long teamId);

    /**
     * Returns all teams.
     *
     * @return List of all teams.
     */
    List<TeamDto> getAllTeams();

    /**
     * Return all team which have certain competitionCountry.
     *
     * @param competitionCountry of teams which well be returned.
     * @return List of teams with competition country.
     */
    List<TeamDto> getTeamsByCountry(CompetitionCountry competitionCountry);

    /**
     * Return team with id.
     *
     * @param id of returned team.
     * @return team which will be returned.
     */
    TeamDto getTeamById(Long id);

    /**
     * Spend amount of money from team budget.
     *
     * @param teamId team id from which will be spend money.
     * @param amount money amount which will be spend.
     * @throws TeamServiceException
     */
    void spendMoneyFromBudget(Long teamId, BigDecimal amount) throws TeamServiceException;

    /**
     * Return price of team.
     *
     * @param teamId team id from which will be returned price.
     * @return amount of price.
     */
    BigDecimal getTeamPrice(Long teamId);

    /**
     * Returns attack skill of team.
     *
     * @param teamId id of team from which we will get attack skill.
     * @return amount of attack skill.
     */
    int getTeamAttackSkill(Long teamId);

    /**
     * Returns defense skill of team.
     *
     * @param teamId id of team from which we will get defense skill.
     * @return amount of defense skill.
     */
    int getTeamDefenseSkill(Long teamId);

    /**
     * Returns team by name.
     *
     * @param name of returned team.
     * @return Team.
     */
    TeamDto findTeamByName(String name);

    void addHockeyPlayer(TeamDto teamDto, HockeyPlayerDto hockeyPlayerDto);

    void removeHockeyPlayer(TeamDto teamDto, HockeyPlayerDto hockeyPlayerDto);
}
