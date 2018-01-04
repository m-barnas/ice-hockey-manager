package cz.muni.fi.pa165.rest.security;

import cz.muni.fi.pa165.dto.HumanPlayerAuthenticateDto;
import cz.muni.fi.pa165.dto.HumanPlayerDto;
import cz.muni.fi.pa165.exceptions.ManagerAuthenticationException;
import cz.muni.fi.pa165.facade.HumanPlayerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountAuthenticationProvider implements AuthenticationProvider {
    private static final String INVALID_CREDENTIALS = "Invalid credentials";

    @Autowired
    private HumanPlayerFacade humanPlayerFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        HumanPlayerAuthenticateDto authenticateDto = new HumanPlayerAuthenticateDto();
        authenticateDto.setEmail(authentication.getName());
        authenticateDto.setPassword((String) authentication.getCredentials());

        HumanPlayerDto humanPlayerDto = humanPlayerFacade.findByEmail(authenticateDto.getEmail());
        if (humanPlayerDto == null) {
            throw new ManagerAuthenticationException(INVALID_CREDENTIALS);
        }

        if (!humanPlayerFacade.authenticate(authenticateDto)) {
            throw new ManagerAuthenticationException(INVALID_CREDENTIALS);
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + humanPlayerDto.getRole().name()));

        return new UsernamePasswordAuthenticationToken(humanPlayerDto, authenticateDto.getPassword(), grantedAuthorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}