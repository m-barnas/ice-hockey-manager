package cz.muni.fi.pa165.rest.config;

import cz.muni.fi.pa165.rest.security.HumanPlayerTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Configuration
@EnableAuthorizationServer
@ComponentScan(basePackages = {"cz.muni.fi.pa165.rest"})
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String WEB_CLIENT_ID = "web-client";
    private static final String REST_CLIENT_ID = "rest-client";
    private static final String WEB_CLIENT_SECRET = "53ac618c-c8d2-44a1-b257-a2bd3816e829";
    private static final String REST_CLIENT_SECRET = "58aa46b5-ddb1-4a29-bed9-55b9f3521280";
    private static final String[] SCOPES = new String[]{"read", "write", "trust"};
    private static final String[] GRANT_TYPES = new String[]{"password", "authorization_code", "refresh_token", "implicit"};
    private static final String[] AUTHORITIES = new String[]{"ROLE_CLIENT", "ROLE_TRUSTED_CLIENT"};
    private static final int WEB_ACCESS_TOKEN_VALIDITY = 300; // seconds
    private static final int REST_ACCESS_TOKEN_VALIDITY = 600;
    private static final int REFRESH_TOKEN_VALIDITY = 1200;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(WEB_CLIENT_ID)
                .authorizedGrantTypes(GRANT_TYPES)
                .authorities(AUTHORITIES)
                .scopes(SCOPES)
                .secret(WEB_CLIENT_SECRET)
                .accessTokenValiditySeconds(WEB_ACCESS_TOKEN_VALIDITY).
                refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY)
                .and().withClient(REST_CLIENT_ID)
                .authorizedGrantTypes(GRANT_TYPES)
                .authorities(AUTHORITIES)
                .scopes(SCOPES)
                .secret(REST_CLIENT_SECRET)
                .accessTokenValiditySeconds(REST_ACCESS_TOKEN_VALIDITY).
                refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancer());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new HumanPlayerTokenEnhancer();
    }
}
