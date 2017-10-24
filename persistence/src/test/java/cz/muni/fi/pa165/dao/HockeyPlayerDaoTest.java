package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = PersistenceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HockeyPlayerDaoTest extends AbstractTestNGSpringContextTests {
}
