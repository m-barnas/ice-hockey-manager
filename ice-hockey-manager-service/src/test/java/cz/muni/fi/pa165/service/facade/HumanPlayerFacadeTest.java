package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDTO;
import cz.muni.fi.pa165.dto.HumanPlayerDTO;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.service.HumanPlayerService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HumanPlayerFacadeTest extends AbstractTestNGSpringContextTests {

    private static final String COMMON_PASSWORD = "password";
    private static final String STRONG_PASSWORD = "1aC5.Ee8-888w";

    @Mock
    private HumanPlayerService humanPlayerService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private HumanPlayerFacade humanPlayerFacade = new HumanPlayerFacadeImpl();

    private HumanPlayer humanPlayer;
    private HumanPlayerDTO humanPlayerDTO;
    private HumanPlayerAuthenticateDTO authenticateDTO;

    private List<HumanPlayer> humanPlayers;
    private List<HumanPlayerDTO> humanPlayerDTOS;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        humanPlayer = createHumanPlayer();
        humanPlayers = new ArrayList<>();
        humanPlayers.add(humanPlayer);

        humanPlayerDTO = createHumanPlayerDTO();
        humanPlayerDTOS = new ArrayList<>();
        humanPlayerDTOS.add(humanPlayerDTO);

        authenticateDTO = createHumanPlayerAuthenticateDTO();

        when(beanMappingService.mapTo(humanPlayer, HumanPlayerDTO.class)).thenReturn(humanPlayerDTO);
        when(beanMappingService.mapTo(humanPlayerDTO, HumanPlayer.class)).thenReturn(humanPlayer);

        when(beanMappingService.mapTo(humanPlayer, HumanPlayerAuthenticateDTO.class)).thenReturn(authenticateDTO);
        when(beanMappingService.mapTo(authenticateDTO, HumanPlayer.class)).thenReturn(humanPlayer);

        when(beanMappingService.mapTo(humanPlayers, HumanPlayerDTO.class)).thenReturn(humanPlayerDTOS);
        when(beanMappingService.mapTo(humanPlayerDTOS, HumanPlayer.class)).thenReturn(humanPlayers);
    }

    @Test
    public void register() throws AuthenticationException {
        // setup
        doNothing().when(humanPlayerService).register(humanPlayer, COMMON_PASSWORD);

        // exercise
        humanPlayerFacade.register(humanPlayerDTO, COMMON_PASSWORD);

        // verify
        verify(humanPlayerService).register(humanPlayer, authenticateDTO.getPassword());
    }

    @Test
    public void changePassword() throws AuthenticationException {
        // setup
        doAnswerSetPasswordHashWhenUpdate(STRONG_PASSWORD);
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        humanPlayerFacade.changePassword(humanPlayerDTO.getId(), COMMON_PASSWORD, STRONG_PASSWORD);

        // verify
        assertThat(humanPlayer.getPasswordHash()).isNotBlank().isEqualTo(STRONG_PASSWORD);
    }

    @Test
    public void delete() {
        // setup
        doNothing().when(humanPlayerService).delete(humanPlayer);
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        humanPlayerFacade.delete(humanPlayerDTO.getId());

        // verify
        verify(humanPlayerService).delete(humanPlayer);
    }

    @Test
    public void findById() {
        // setup
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDTO result = humanPlayerFacade.findById(humanPlayerDTO.getId());

        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDTO);
    }

    @Test
    public void findByEmail() {
        // setup
        when(humanPlayerService.findByEmail(humanPlayer.getEmail())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDTO result = humanPlayerFacade.findByEmail(humanPlayerDTO.getEmail());

        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDTO);
    }

    @Test
    public void findByUsername() {
        // setup
        when(humanPlayerService.findByUsername(humanPlayer.getUsername())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDTO result = humanPlayerFacade.findByUsername(humanPlayerDTO.getUsername());

        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDTO);
    }

    @Test
    public void findAll() {
        // setup
        when(humanPlayerService.findAll()).thenReturn(humanPlayers);

        // exercise
        Collection<HumanPlayerDTO> result = humanPlayerFacade.findAll();

        // verify
        assertThat(result).isEqualTo(humanPlayerDTOS);
    }

    private void doAnswerSetPasswordHashWhenUpdate(String passwordHash) throws AuthenticationException {
        doAnswer((Answer<Void>) invocationOnMock -> {
            humanPlayer.setPasswordHash(passwordHash);
            return null;
        }).when(humanPlayerService).changePassword(humanPlayer.getId(), COMMON_PASSWORD, STRONG_PASSWORD);
    }

    private HumanPlayer createHumanPlayer() {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setId(1L);
        humanPlayer.setUsername("admin");
        humanPlayer.setRole(Role.ADMIN);
        humanPlayer.setEmail("admin@admin.com");
        return humanPlayer;
    }

    private HumanPlayerDTO createHumanPlayerDTO() {
        HumanPlayerDTO humanPlayerDTO = new HumanPlayerDTO();
        humanPlayerDTO.setId(1L);
        humanPlayerDTO.setUsername("admin");
        humanPlayerDTO.setRole(Role.ADMIN);
        humanPlayerDTO.setEmail("admin@admin.com");
        return humanPlayerDTO;
    }

    private HumanPlayerAuthenticateDTO createHumanPlayerAuthenticateDTO() {
        HumanPlayerAuthenticateDTO authenticateDTO = new HumanPlayerAuthenticateDTO();
        authenticateDTO.setEmail("admin@admin.com");
        authenticateDTO.setPassword(COMMON_PASSWORD);
        return authenticateDTO;
    }
}
