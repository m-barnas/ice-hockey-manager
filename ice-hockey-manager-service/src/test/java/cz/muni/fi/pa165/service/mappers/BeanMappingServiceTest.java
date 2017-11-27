package cz.muni.fi.pa165.service.mappers;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDto;
import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    private HumanPlayer humanPlayer;

    private HumanPlayerDto humanPlayerDTO;

    private HumanPlayerAuthenticateDto authenticateDTO;

    @BeforeMethod
    public void prepareData(){
        humanPlayer = createHumanPlayer();
        humanPlayerDTO = createHumanPlayerDto();
        authenticateDTO = createHumanPlayerAuthenticateDto();
    }

    @Test
    public void shouldMapHumanPlayerToHumanPlayerDTO(){
        assertThat(beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class)).isEqualTo(humanPlayerDTO);
    }

    @Test
    public void shouldMapHumanPlayerToHumanPlayerAuthenticateDTO(){
        assertThat(beanMappingService.mapTo(humanPlayer, HumanPlayerAuthenticateDto.class)).isEqualTo(authenticateDTO);
    }

    @Test
    public void shouldMapHumanPlayerDTOToHumanPlayer(){
        assertThat(beanMappingService.mapTo(humanPlayerDTO, HumanPlayer.class)).isEqualTo(humanPlayer);
    }

    @Test
    public void shouldMapHumanPlayerAuthenticateDTOToHumanPlayer(){
        assertThat(beanMappingService.mapTo(authenticateDTO, HumanPlayer.class)).isEqualTo(humanPlayer);
    }

    private HumanPlayer createHumanPlayer() {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setId(1L);
        humanPlayer.setUsername("user");
        humanPlayer.setRole(Role.USER);
        humanPlayer.setEmail("user@user.com");
        return humanPlayer;
    }

    private HumanPlayerDto createHumanPlayerDto() {
        HumanPlayerDto humanPlayerDTO = new HumanPlayerDto();
        humanPlayerDTO.setId(1L);
        humanPlayerDTO.setUsername("user");
        humanPlayerDTO.setRole(Role.USER);
        humanPlayerDTO.setEmail("user@user.com");
        return humanPlayerDTO;
    }

    private HumanPlayerAuthenticateDto createHumanPlayerAuthenticateDto() {
        HumanPlayerAuthenticateDto authenticateDTO = new HumanPlayerAuthenticateDto();
        authenticateDTO.setEmail("user@user.com");
        authenticateDTO.setPassword("password");
        return authenticateDTO;
    }

}
