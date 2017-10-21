package cz.muni.fi.pa165.repositories;

import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team,Long> {
    Team findById(Long id);
    Team findByName(String name);
    List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry);
    List<Team> finAll();

}
