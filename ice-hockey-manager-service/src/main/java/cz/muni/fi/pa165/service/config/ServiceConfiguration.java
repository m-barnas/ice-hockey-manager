package cz.muni.fi.pa165.service.config;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Configuration
@Import(PersistenceConfiguration.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.service")
public class ServiceConfiguration {
}