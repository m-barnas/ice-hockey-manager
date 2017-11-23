package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
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

    private HumanPlayer humanPlayer2;

    @BeforeMethod
    public void setUp() {
        humanPlayer = new HumanPlayer();
        humanPlayer.setUsername("someone");
        humanPlayer.setPasswordHash("xxx");
        humanPlayer.setEmail("someone@gmail.com");
        humanPlayer.setRole(Role.USER);

        humanPlayer2 = new HumanPlayer();
        humanPlayer2.setUsername("Petr Admin");
        humanPlayer2.setPasswordHash("bbbb");
        humanPlayer2.setEmail("c@cc.cz");
        humanPlayer2.setRole(Role.ADMIN);
    }

    @Test
    public void save() {
        humanPlayerDao.create(humanPlayer);
        assertThat(humanPlayer.getId()).isNotNull();
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(humanPlayerDao.findById(humanPlayer.getId())).isEqualToComparingFieldByField(humanPlayer);

        humanPlayerDao.create(humanPlayer2);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(2);
    }

    @Test
    public void saveWithNullUsername() {
        humanPlayer.setUsername(null);
        assertThatThrownBy(() -> humanPlayerDao.create(humanPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void saveWithNullEmail() {
        humanPlayer.setEmail(null);
        assertThatThrownBy(() -> humanPlayerDao.create(humanPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void saveWithNullPasswordHash() {
        humanPlayer.setPasswordHash(null);
        assertThatThrownBy(() -> humanPlayerDao.create(humanPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void saveWithNullRole() {
        humanPlayer.setRole(null);
        assertThatThrownBy(() -> humanPlayerDao.create(humanPlayer)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void update() {
        humanPlayerDao.create(humanPlayer);
        humanPlayer.setRole(Role.ADMIN);
        humanPlayerDao.update(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
        assertThat(humanPlayerDao.findById(humanPlayer.getId()).getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    public void delete() {
        humanPlayerDao.create(humanPlayer);
        humanPlayerDao.create(humanPlayer2);
        humanPlayerDao.delete(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void deleteNotExistingEntity() {
        humanPlayerDao.create(humanPlayer2);
        humanPlayerDao.delete(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
    }

    @Test
    public void findById() {
        assertThat(humanPlayerDao.findById(1l)).isNull();
        humanPlayerDao.create(humanPlayer);
        assertThat(humanPlayerDao.findById(humanPlayer.getId())).isEqualToComparingFieldByField(humanPlayer);
    }

    @Test
    public void findByEmail() {
        humanPlayerDao.create(humanPlayer);
        assertThat(humanPlayerDao.findByEmail(humanPlayer.getEmail())).isNotNull();
        assertThat(humanPlayerDao.findByEmail("poihedb")).isNull();
   }

    @Test
    public void findByUsername() {
        humanPlayerDao.create(humanPlayer);
        assertThat(humanPlayerDao.findByUsername(humanPlayer.getUsername())).isNotNull();
        assertThat(humanPlayerDao.findByUsername("poihedb")).isNull();
    }

    @Test
    public void findAll() {
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(0);
        humanPlayerDao.create(humanPlayer);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(1);
        humanPlayerDao.create(humanPlayer2);
        assertThat(humanPlayerDao.findAll().size()).isEqualTo(2);
   }
}
