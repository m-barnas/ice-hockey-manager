package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;

import java.util.List;

public interface TeamDao {

    /**
     *  Create
     *
     * @param team to create
     */
    void create(Team team);

    /**
     * Update
     *
     * @param team to update
     * @return updated team
     */
    Team update(Team team);

    /**
     * Delete
     *
     * @param team to delete
     */
    void delete(Team team);

    /**
     * Find by id.
     *
     * @param id to find
     * @return team with given id
     */
    Team findById(Long id);

    /**
     *
     * @param name to find
     * @return team with given name
     */
    Team findByName(String name);

    /**
     *
     * @param humanPlayer to find
     * @return team with given human player
     */
    Team findByHumanPlayer(HumanPlayer humanPlayer);

    /**
     *
     * @param competitionCountry to find
     * @return teams with given competition country
     */
    List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry);

    /**
     *
     * @return all team
     */
    List<Team> findAll();

}
