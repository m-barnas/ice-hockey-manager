package cz.muni.fi.pa165.repositories;

import cz.muni.fi.pa165.entity.HumanPlayer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public interface HumanPlayerRepository extends CrudRepository<HumanPlayer, Long> {

  HumanPlayer findById(Long id);

  HumanPlayer findByEmail(String email);

  HumanPlayer findByUsernameIgnoreCase(String username);

  List<HumanPlayer> findAll();
}
