package cz.muni.fi.pa165.service.config;

import cz.muni.fi.pa165.config.PersistenceConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");
        dozer.setMappingFiles(mappingFiles);
        return dozer;
    }
}
