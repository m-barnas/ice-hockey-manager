package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void create(Team team) {
        teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry) {
        return teamRepository.findByCompetitionCountry(competitionCountry);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
