package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDto;
import cz.muni.fi.pa165.dto.HumanPlayerDto;
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
    private HumanPlayerDto humanPlayerDto;
    private HumanPlayerAuthenticateDto authenticateDto;

    private List<HumanPlayer> humanPlayers;
    private List<HumanPlayerDto> humanPlayerDtoS;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void prepareData() {
        humanPlayer = createHumanPlayer();
        humanPlayers = new ArrayList<>();
        humanPlayers.add(humanPlayer);

        humanPlayerDto = createHumanPlayerDto();
        humanPlayerDtoS = new ArrayList<>();
        humanPlayerDtoS.add(humanPlayerDto);

        authenticateDto = createHumanPlayerAuthenticateDto();

        when(beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class)).thenReturn(humanPlayerDto);
        when(beanMappingService.mapTo(humanPlayerDto, HumanPlayer.class)).thenReturn(humanPlayer);

        when(beanMappingService.mapTo(humanPlayer, HumanPlayerAuthenticateDto.class)).thenReturn(authenticateDto);
        when(beanMappingService.mapTo(authenticateDto, HumanPlayer.class)).thenReturn(humanPlayer);

        when(beanMappingService.mapTo(humanPlayers, HumanPlayerDto.class)).thenReturn(humanPlayerDtoS);
        when(beanMappingService.mapTo(humanPlayerDtoS, HumanPlayer.class)).thenReturn(humanPlayers);
    }

    @Test
    public void register() throws AuthenticationException {
        // setup
        doNothing().when(humanPlayerService).register(humanPlayer, COMMON_PASSWORD);

        // exercise
        humanPlayerFacade.register(humanPlayerDto, COMMON_PASSWORD);

        // verify
        verify(humanPlayerService).register(humanPlayer, authenticateDto.getPassword());
    }

    @Test
    public void changePassword() throws AuthenticationException {
        // setup
        doAnswerSetPasswordHashWhenUpdate(STRONG_PASSWORD);
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        humanPlayerFacade.changePassword(humanPlayerDto.getId(), COMMON_PASSWORD, STRONG_PASSWORD);

        // verify
        assertThat(humanPlayer.getPasswordHash()).isNotBlank().isEqualTo(STRONG_PASSWORD);
    }

    @Test
    public void delete() {
        // setup
        doNothing().when(humanPlayerService).delete(humanPlayer);
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        humanPlayerFacade.delete(humanPlayerDto.getId());

        // verify
        verify(humanPlayerService).delete(humanPlayer);
    }

    @Test
    public void findById() {
        // setup
        when(humanPlayerService.findById(humanPlayer.getId())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDto result = humanPlayerFacade.findById(humanPlayerDto.getId());

        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDto);
    }

    @Test
    public void findByEmail() {
        // setup
        when(humanPlayerService.findByEmail(humanPlayer.getEmail())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDto result = humanPlayerFacade.findByEmail(humanPlayerDto.getEmail());


        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDto);
    }

    @Test
    public void findByUsername() {
        // setup
        when(humanPlayerService.findByUsername(humanPlayer.getUsername())).thenReturn(humanPlayer);

        // exercise
        HumanPlayerDto result = humanPlayerFacade.findByUsername(humanPlayerDto.getUsername());

        // verify
        assertThat(result).isEqualToComparingFieldByField(humanPlayerDto);
    }

    @Test
    public void findAll() {
        // setup
        when(humanPlayerService.findAll()).thenReturn(humanPlayers);

        // exercise
        Collection<HumanPlayerDto> result = humanPlayerFacade.findAll();

        // verify
        assertThat(result).isEqualTo(humanPlayerDtoS);
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

    private HumanPlayerDto createHumanPlayerDto() {
        HumanPlayerDto humanPlayerDto = new HumanPlayerDto();
        humanPlayerDto.setId(1L);
        humanPlayerDto.setUsername("admin");
        humanPlayerDto.setRole(Role.ADMIN);
        humanPlayerDto.setEmail("admin@admin.com");
        return humanPlayerDto;
    }

    private HumanPlayerAuthenticateDto createHumanPlayerAuthenticateDto() {
        HumanPlayerAuthenticateDto authenticateDto = new HumanPlayerAuthenticateDto();
        authenticateDto.setEmail("admin@admin.com");
        authenticateDto.setPassword(COMMON_PASSWORD);
        return authenticateDto;
    }
}
