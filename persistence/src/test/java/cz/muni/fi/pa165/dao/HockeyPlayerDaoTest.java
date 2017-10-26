package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import cz.muni.fi.pa165.entity.HockeyPlayer;
import cz.muni.fi.pa165.entity.Team;
import cz.muni.fi.pa165.enums.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.inject.Inject;
import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HockeyPlayerDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    HockeyPlayerDao hockeyPlayerDao;

    HockeyPlayer hockeyPlayer;

    @org.testng.annotations.BeforeClass
    public void setUp() {
        hockeyPlayer = new HockeyPlayer();
        hockeyPlayer.setName("Jan Novák");
        hockeyPlayer.setPost(Position.LW);
        hockeyPlayer.setAttackSkill(10);
        hockeyPlayer.setDefenseSkill(23);
    }


    @Test
    public void createHockeyPlayer() {
       hockeyPlayerDao.create(hockeyPlayer);
       assertThat(hockeyPlayerDao.findAll().size()).isEqualTo(1);
    }

}
