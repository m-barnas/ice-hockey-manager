package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.HumanPlayer;

import java.util.List;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public interface HumanPlayerDao {

  /**
   * Save {@link HumanPlayer}.
   *
   * @param humanPlayer to save
   * @return saved human player
   */
  HumanPlayer save(HumanPlayer humanPlayer);

  /**
   * Delete {@link HumanPlayer}.
   *
   * @param humanPlayer to delete
   */
  void delete(HumanPlayer humanPlayer);

  /**
   * Find {@link HumanPlayer} by id.
   *
   * @param id to find
   * @return human player with given id
   */
  HumanPlayer findById(Long id);

  /**
   * Find {@link HumanPlayer} by email.
   *
   * @param email to find
   * @return human player with given email
   */
  HumanPlayer findByEmail(String email);

  /**
   * Find {@link HumanPlayer} by username.
   *
   * @param username to find
   * @return human player with given username
   */
  HumanPlayer findByUsername(String username);

  /**
   * Find all {@link HumanPlayer}s.
   *
   * @return all human players
   */
  List<HumanPlayer> findAll();
}
