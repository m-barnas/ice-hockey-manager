package cz.muni.fi.pa165.rest.security;

import cz.muni.fi.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static cz.muni.fi.pa165.rest.ApiUri.*;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "rest_api";

    private static final String[] postAuthenticated = new String[] {
            ROOT_URI_MANAGERS + SubApiUri.CHANGE_PASSWD + SubApiUri.ALL,
            ROOT_URI_TEAMS + SubApiUri.ALL
    };

    private static final String[] postAdmin = new String[] {
            ROOT_URI_GAMES + SubApiUri.CREATE + SubApiUri.ALL,
            ROOT_URI_MANAGERS + SubApiUri.UPDATE + SubApiUri.ALL
    };

    private static final String[] putAdmin = new String[] {
            ROOT_URI_GAMES + SubApiUri.ONE_SEGMENT,
            ROOT_URI_GAMES + SubApiUri.ALL,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.CREATE + SubApiUri.ALL,
            ROOT_URI_TEAMS + SubApiUri.CREATE + SubApiUri.ALL
    };

    private static final String[] deleteAdmin = new String[] {
            ROOT_URI_MANAGERS + SubApiUri.ONE_SEGMENT,
            ROOT_URI_GAMES + SubApiUri.ONE_SEGMENT,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.ONE_SEGMENT,
            ROOT_URI_TEAMS + SubApiUri.ONE_SEGMENT
    };


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, postAuthenticated).authenticated()
                .antMatchers(HttpMethod.POST, postAdmin).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, putAdmin).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, deleteAdmin).hasRole(Role.ADMIN.name())
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
