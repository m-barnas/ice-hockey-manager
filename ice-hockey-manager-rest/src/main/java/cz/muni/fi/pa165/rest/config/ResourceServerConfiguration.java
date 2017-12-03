package cz.muni.fi.pa165.rest.config;

import cz.muni.fi.pa165.enums.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import static cz.muni.fi.pa165.rest.ApiUri.*;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "rest_api";

    private static final String[] get = new String[]{
            ROOT_URI_HOME + SubApiUri.ALL,
            ROOT_URI_TEAMS + SubApiUri.ALL,
            ROOT_URI_GAMES + SubApiUri.ALL,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.ALL,
            ROOT_URI_MANAGERS + SubApiUri.ALL
    };

    private static final String[] patch = new String[]{
            ROOT_URI_TEAMS + SubApiUri.ALL,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.ALL
    };

    private static final String[] adminPost = new String[]{
            ROOT_URI_TEAMS + SubApiUri.CREATE + SubApiUri.ALL,
            ROOT_URI_GAMES + SubApiUri.CREATE + SubApiUri.ALL,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.CREATE + SubApiUri.ALL
    };

    private static final String[] adminPatch = new String[]{
            ROOT_URI_TEAMS + SubApiUri.UPDATE + SubApiUri.ALL,
            ROOT_URI_GAMES + SubApiUri.UPDATE + SubApiUri.ALL,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.UPDATE + SubApiUri.ALL,
            ROOT_URI_MANAGERS + SubApiUri.UPDATE + SubApiUri.ALL
    };

    private static final String[] adminDelete = new String[]{
            ROOT_URI_TEAMS + SubApiUri.ONE_SEGMENT,
            ROOT_URI_GAMES + SubApiUri.ONE_SEGMENT,
            ROOT_URI_HOCKEY_PLAYERS + SubApiUri.ONE_SEGMENT,
            ROOT_URI_MANAGERS + SubApiUri.ONE_SEGMENT
    };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, get).authenticated()
                .antMatchers(HttpMethod.PATCH, patch).authenticated()
                .antMatchers(HttpMethod.POST, adminPost).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PATCH, adminPatch).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, adminDelete).hasRole(Role.ADMIN.name())
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
