package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;

import java.util.List;

public interface TeamDao {

    /**
     * Update
     *
     * @param team to save
     * @return updated team
     */
    Team save(Team team);

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
