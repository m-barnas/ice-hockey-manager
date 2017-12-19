package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.HumanPlayerDao;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.ManagerAuthenticationException;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.exceptions.IncorrectPasswordException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class HumanPlayerServiceTest extends AbstractTestNGSpringContextTests {

    private static final String COMMON_PASSWORD = "password";
    private static final String STRONG_PASSWORD = "1aC5.Ee8-888w";
    private static final String TOO_SHORT_PASSWORD = "Ims";
    private static final String TOO_LONG_PASSWORD = "Imtoolongpassword";

    @Mock
    private HumanPlayerDao humanPlayerDao;

    @Autowired
    @InjectMocks
    private HumanPlayerService humanPlayerService;

    private HumanPlayer user;

    private HumanPlayer admin;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        user = createHumanPlayer(Role.USER);
        admin = createHumanPlayer(Role.ADMIN);
    }

    @Test
    public void register() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);

        // exercise
        humanPlayerService.register(user, COMMON_PASSWORD);

        // verify
        assertThat(user.getId()).isNotNull().isEqualTo(1L);
    }

    @Test
    public void registerWithHumanPlayerNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.register(null, COMMON_PASSWORD))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void registerWithPasswordNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.register(user, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void registerWithEmptyPassword() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.register(user, ""))
                .isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    public void registerWithPasswordTooShort() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.register(user, TOO_SHORT_PASSWORD))
                .isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    public void registerWithPasswordTooLong() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.register(user, TOO_LONG_PASSWORD))
                .isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    public void authenticate() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);
        humanPlayerService.register(user, COMMON_PASSWORD);

        // exercise
        boolean authenticated = humanPlayerService.authenticate(user, COMMON_PASSWORD);

        // verify
        assertThat(authenticated).isTrue();
    }

    @Test
    public void authenticateWithHumanPlayerNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.authenticate(null, COMMON_PASSWORD))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void authenticateUnencryptedPasswordNull() throws ManagerAuthenticationException {
        // exercise
        boolean authenticated = humanPlayerService.authenticate(user, null);

        // verify
        assertThat(authenticated).isFalse();
    }

    @Test
    public void authenticateWithWrongPassword() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);
        humanPlayerService.register(user, COMMON_PASSWORD);

        // exercise
        boolean authenticated = humanPlayerService.authenticate(user, STRONG_PASSWORD);

        // verify
        assertThat(authenticated).isFalse();
    }

    @Test
    public void changePassword() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);
        humanPlayerService.register(user, COMMON_PASSWORD);
        when(humanPlayerDao.findById(user.getId())).thenReturn(user);
        doAnswerSetIdWhenCreate(admin);
        humanPlayerService.register(admin, STRONG_PASSWORD);
        doAnswerSetPasswordHashWhenUpdate(admin.getPasswordHash());

        // exercise
        humanPlayerService.changePassword(user.getId(), COMMON_PASSWORD, STRONG_PASSWORD);

        // verify
        assertThat(humanPlayerService.authenticate(user, COMMON_PASSWORD)).isFalse();
        assertThat(humanPlayerService.authenticate(user, STRONG_PASSWORD)).isTrue();
    }

    @Test
    public void changePasswordWithWrongOldUnencryptedPassword() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);
        humanPlayerService.register(user, COMMON_PASSWORD);
        when(humanPlayerDao.findById(user.getId())).thenReturn(user);

        // exercise
        humanPlayerService.changePassword(user.getId(), STRONG_PASSWORD, STRONG_PASSWORD);

        // verify
        assertThat(humanPlayerService.authenticate(user, COMMON_PASSWORD)).isTrue();
        assertThat(humanPlayerService.authenticate(user, STRONG_PASSWORD)).isFalse();
    }

    @Test
    public void changePasswordWithHumanPlayerNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.changePassword(null, COMMON_PASSWORD,
                STRONG_PASSWORD)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void changePasswordWithOldUnencryptedPasswordNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.changePassword(1L, null,
                STRONG_PASSWORD)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void changePasswordWithNewUnencryptedPasswordNull() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.changePassword(1L, COMMON_PASSWORD,
                null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void changePasswordWithNewUnencryptedPasswordTooShort() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.changePassword(1L, COMMON_PASSWORD,
                TOO_SHORT_PASSWORD)).isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    public void changePasswordWithNewUnencryptedPasswordTooLong() throws ManagerAuthenticationException {
        // exercise + verify
        assertThatThrownBy(() -> humanPlayerService.changePassword(1L, COMMON_PASSWORD,
                TOO_LONG_PASSWORD)).isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    public void changeRole() throws ManagerAuthenticationException {
        // setup
        doAnswerSetIdWhenCreate(user);
        doAnswerSetRoleWhenUpdate(Role.ADMIN);
        humanPlayerService.register(user, COMMON_PASSWORD);
        when(humanPlayerDao.findById(user.getId())).thenReturn(user);

        // exercise
        humanPlayerService.changeRole(user.getId(), Role.ADMIN);

        // verify
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    public void delete() {
        // setup
        doAnswer((Answer<Void>) invocationOnMock -> {
            HumanPlayer h = (HumanPlayer) invocationOnMock.getArguments()[0];
            h.setUsername("Deleted");
            return null;
        }).when(humanPlayerDao).delete(user);

        // exercise
        humanPlayerService.delete(user);

        // verify
        assertThat(user.getUsername()).isNotNull().isEqualTo("Deleted");
    }

    @Test
    public void findById() {
        when(humanPlayerDao.findById(user.getId())).thenReturn(user);
        assertThat(user).isEqualToComparingFieldByField(humanPlayerService.findById(user.getId()));
    }

    @Test
    public void findByEmail() {
        when(humanPlayerDao.findByEmail(user.getEmail())).thenReturn(user);
        assertThat(user).isEqualToComparingFieldByField(humanPlayerService.findByEmail(user.getEmail()));
    }

    @Test
    public void findByUsername() {
        when(humanPlayerDao.findByUsername(user.getUsername())).thenReturn(user);
        assertThat(user).isEqualToComparingFieldByField(humanPlayerService.findByUsername(user.getUsername()));
    }

    @Test
    public void testFindAll() {
        when(humanPlayerDao.findAll()).thenReturn(new ArrayList<>());
        assertThat(new ArrayList<>()).isEqualTo(humanPlayerService.findAll());
    }

    private void doAnswerSetIdWhenCreate(HumanPlayer humanPlayer) {
        doAnswer((Answer<Void>) invocationOnMock -> {
            HumanPlayer h = (HumanPlayer) invocationOnMock.getArguments()[0];
            h.setId(1L);
            return null;
        }).when(humanPlayerDao).create(humanPlayer);
    }

    private void doAnswerSetPasswordHashWhenUpdate(String passwordHash) {
        doAnswer((Answer<Void>) invocationOnMock -> {
            HumanPlayer h = (HumanPlayer) invocationOnMock.getArguments()[0];
            h.setPasswordHash(passwordHash);
            return null;
        }).when(humanPlayerDao).update(user);
    }

    private void doAnswerSetRoleWhenUpdate(Role role) {
        doAnswer((Answer<Void>) invocationOnMock -> {
            HumanPlayer h = (HumanPlayer) invocationOnMock.getArguments()[0];
            h.setRole(role);
            return null;
        }).when(humanPlayerDao).update(user);
    }

    private HumanPlayer createHumanPlayer(Role role) {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setUsername("admin");
        humanPlayer.setRole(role);
        humanPlayer.setEmail("admin@admin.com");
        humanPlayer.setPasswordHash("password_hash");
        return humanPlayer;
    }
}
