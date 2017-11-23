package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.CompetitionCountry;
import cz.muni.fi.pa165.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {


    @Autowired
    private EntityManager entityManager;

    @Override
    public void create(Team team) {
        entityManager.persist(team);
    }

    public Team update(Team team) {
        return entityManager.merge(team);
    }

    @Override
    public void delete(Team team) {
        entityManager.remove(team);
    }

    @Override
    public Team findById(Long id) {
        return entityManager.find(Team.class, id);
    }

    @Override
    public Team findByName(String name) {
        return (Team) entityManager.createQuery("SELECT team FROM Team team WHERE team.name = :name").setParameter("name", name).getSingleResult();
    }

    @Override
    public List<Team> findByCompetitionCountry(CompetitionCountry competitionCountry) {
        return (List<Team>) entityManager.createQuery("SELECT team FROM Team team WHERE team.competitionCountry = :competitionCountry").setParameter("competitionCountry", competitionCountry).getResultList();
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) entityManager.createQuery("FROM Team").getResultList();
    }
}
