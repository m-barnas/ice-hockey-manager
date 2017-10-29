package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for HumanPlayerDaoImpl class.
 *
 * @author Marketa Elederova
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HumanPlayerDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HumanPlayerDao humanPlayerDao;

    private HumanPlayer humanPlayer;

    @BeforeClass
    public void setUp() {

        humanPlayer = new HumanPlayer();
        humanPlayer.setUsername("someone");
        humanPlayer.setPasswordHash("xxx");
        humanPlayer.setEmail("someone@gmail.com");
        humanPlayer.setRole(Role.USER);
    }

    @Test
    public void save() {

        humanPlayer = humanPlayerDao.save(humanPlayer);
        assertThat(humanPlayer.getId()).isNotNull();
        List<HumanPlayer> humanPlayers = humanPlayerDao.findAll();
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(humanPlayers.get(0).getUsername()).isEqualTo("someone");
        assertThat(humanPlayers.get(0).getPasswordHash()).isEqualTo("xxx");
        assertThat(humanPlayers.get(0).getEmail()).isEqualTo("someone@gmail.com");
        assertThat(humanPlayers.get(0).getRole()).isEqualTo(Role.USER);

        humanPlayer.setRole(Role.ADMIN);
        humanPlayerDao.save(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(humanPlayerDao.findAll().get(0).getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    public void delete() {

        humanPlayerDao.delete(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(0);
        humanPlayer = humanPlayerDao.save(humanPlayer);
    }

    @Test
    public void findById() {

        assertThat(humanPlayerDao.findById(humanPlayer.getId())).isNotNull();
        assertThat(humanPlayerDao.findById(humanPlayer.getId() + 1)).isNull();
    }

    @Test
    public void findByEmail() {

        assertThat(humanPlayerDao.findByEmail(humanPlayer.getEmail())).isNotNull();
        assertThat(humanPlayerDao.findByEmail("poihedb")).isNull();
   }

    @Test
    public void findByUsername() {

        assertThat(humanPlayerDao.findByUsername(humanPlayer.getUsername())).isNotNull();
        assertThat(humanPlayerDao.findByUsername("poihedb")).isNull();
   }

    @Test
    public void findAll() {

        for (HumanPlayer hp : humanPlayerDao.findAll()) {
            humanPlayerDao.delete(hp);
        }
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(0);

        humanPlayerDao.save(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);

        HumanPlayer humanPlayer2 = new HumanPlayer();
        humanPlayer2.setUsername("a");
        humanPlayer2.setPasswordHash("b");
        humanPlayer2.setEmail("c@cc.cz");
        humanPlayer2.setRole(Role.USER);
        humanPlayerDao.save(humanPlayer2);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(2);
   }
}
