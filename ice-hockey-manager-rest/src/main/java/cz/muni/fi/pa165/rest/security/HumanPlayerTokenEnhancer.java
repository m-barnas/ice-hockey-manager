package cz.muni.fi.pa165.rest.security;

import cz.muni.fi.pa165.dto.HumanPlayerDto;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class HumanPlayerTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        HumanPlayerDto humanPlayerDto = (HumanPlayerDto) oAuth2Authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("role", humanPlayerDto.getRole().name());
        additionalInfo.put("id", humanPlayerDto.getId());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
