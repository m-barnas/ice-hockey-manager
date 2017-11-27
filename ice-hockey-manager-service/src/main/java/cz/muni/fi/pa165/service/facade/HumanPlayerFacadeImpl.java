package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDto;
import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.entity.HumanPlayer;
import cz.muni.fi.pa165.enums.Role;
import cz.muni.fi.pa165.exceptions.AuthenticationException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import cz.muni.fi.pa165.service.HumanPlayerService;
import cz.muni.fi.pa165.service.mappers.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class  HumanPlayerFacadeImpl implements HumanPlayerFacade {

    @Autowired
    private HumanPlayerService humanPlayerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void register(HumanPlayerDto humanPlayerDto, String unencryptedPassword) throws AuthenticationException {
        HumanPlayer humanPlayer = beanMappingService.mapTo(humanPlayerDto, HumanPlayer.class);
        humanPlayerService.register(humanPlayer, unencryptedPassword);
        humanPlayerDto.setId(humanPlayer.getId());
    }

    @Override
    public boolean authenticate(HumanPlayerAuthenticateDto authenticateDTO)
            throws AuthenticationException {
        return humanPlayerService.authenticate(
                humanPlayerService.findByEmail(authenticateDTO.getEmail()), authenticateDTO.getPassword());
    }

    @Override
    public HumanPlayerDto changePassword(Long humanPlayerId, String oldUnencryptedPassword,
                                         String newUnencryptedPassword) throws AuthenticationException {
        HumanPlayer humanPlayer = humanPlayerService.changePassword(humanPlayerId, oldUnencryptedPassword,
                newUnencryptedPassword);
        return beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class);
    }

    @Override
    public HumanPlayerDto changeRole(Long humanPlayerId, Role role) {
        HumanPlayer humanPlayer = humanPlayerService.changeRole(humanPlayerId, role);
        return beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class);
    }

    @Override
    public void delete(Long humanPlayerId) {
        HumanPlayer humanPlayer = humanPlayerService.findById(humanPlayerId);
        humanPlayerService.delete(humanPlayer);
    }

    @Override
    public HumanPlayerDto findById(Long id) {
        HumanPlayer humanPlayer = humanPlayerService.findById(id);
        return (humanPlayer == null) ? null : beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class);
    }

    @Override
    public HumanPlayerDto findByEmail(String email) {
        HumanPlayer humanPlayer = humanPlayerService.findByEmail(email);
        return (humanPlayer == null) ? null : beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class);
    }

    @Override
    public HumanPlayerDto findByUsername(String username) {
        HumanPlayer humanPlayer = humanPlayerService.findByUsername(username);
        return (humanPlayer == null) ? null : beanMappingService.mapTo(humanPlayer, HumanPlayerDto.class);
    }

    @Override
    public List<HumanPlayerDto> findAll() {
        return beanMappingService.mapTo(humanPlayerService.findAll(), HumanPlayerDto.class);
    }
}
