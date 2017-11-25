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
    /**
     * Returns team by id.
     * @param id of team which will be returned.
     * @return Team.
     */
    Team findById(Long id);

    /**
     * Returns all teams.
     *
     * @return List of teams.
     */
    List<Team> findAll();

    /**
     * Returns all teams with specified competition country.
     *
     * @param competitionCountry of teams.
     * @return list of teams.
     */
    List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry);

    /**
     * Returns team by name.
     *
     * @param name of returned team.
     * @return Team.
     */
    Team findByName(String name);

    /**
     * Creates new team.
     *
     * @param team to create.
     * @return Created team with new id field.
     */
    Team createTeam(Team team);

    /**
     * Removes team.
     *
     * @param team to remove.
     */
    void deleteTeam(Team team);

    /**
     * Adds hockey player to team.
     *
     * @param team to which will be hockey player added.
     * @param hockeyPlayer which will be added to team.
     */
    void addHockeyPlayer(Team team, HockeyPlayer hockeyPlayer);

    /**
     * Removes hockey player from team.
     *
     * @param team from which will be hockey player remove.
     * @param hockeyPlayer which will be removed from team.
     */
    void removeHockeyPlayer(Team team, HockeyPlayer hockeyPlayer);

    /**
     * Spends money form budget.
     *
     * @param team from which will be money spend.
     * @param amount of team which will be spend from team budget
     * @throws TeamServiceException if amount is null or new budget is less than zero
     */
    void spendMoneyFromBudget(Team team, BigDecimal amount) throws TeamServiceException;

    /**
     * Returns team price.
     *
     * @param team which price will be returned.
     * @return amount of team price.
     */
    BigDecimal getTeamPrice(Team team);

    /**
     * Returns attack skill of team.
     *
     * @param team which attack skill will be removed.
     * @return amount of attack skill.
     */
    int getTeamAttackSkill(Team team);

    /**
     * Returns defense skill of team.
     *
     * @param team which defense skill will be removed.
     * @return amount of defense skill.
     */
    int getTeamDefenseSkill(Team team);
}
