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

    private static final String[] changePassword = new String[]{
            ROOT_URI_MANAGERS + SubApiUri.CHANGE_PASSWD + SubApiUri.ALL
    };

    private static final String[] changeRole = new String[]{
            ROOT_URI_MANAGERS + SubApiUri.UPDATE + SubApiUri.ALL
    };

    private static final String[] deleteManager = new String[]{
            ROOT_URI_MANAGERS + SubApiUri.ONE_SEGMENT
    };

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, changePassword).authenticated()
                .antMatchers(HttpMethod.POST, changeRole).hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, deleteManager).hasRole(Role.ADMIN.name())
                .and()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
