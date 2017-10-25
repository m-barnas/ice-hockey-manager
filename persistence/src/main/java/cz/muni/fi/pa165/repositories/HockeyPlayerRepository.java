package cz.muni.fi.pa165.repositories;

import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
public interface HockeyPlayerRepository extends CrudRepository<HockeyPlayer, Long> {

	HockeyPlayer findById(Long id);

	HockeyPlayer findByName(String name);

	List<HockeyPlayer> findByPost(Position post);

	List<HockeyPlayer> findByTeam(Team team);

	List<HockeyPlayer> findByPriceIsLessThanEqual(Long price);

	List<HockeyPlayer> findAll();
}
