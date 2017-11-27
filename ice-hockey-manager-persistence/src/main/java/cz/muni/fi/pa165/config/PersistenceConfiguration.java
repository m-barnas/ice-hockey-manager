package cz.muni.fi.pa165.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackages = "cz.muni.fi.pa165.dao")
@PropertySource("classpath:config.properties")
public class PersistenceConfiguration {

    @Autowired
    private Environment env;

    /**
     * Enables automatic translation of exceptions to DataAccessExceptions.
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor postProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    /**
     * Starts up a container that emulates behavior prescribed in JPA spec for container-managed EntityManager
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(db());
        jpaFactoryBean.setLoadTimeWeaver(instrumentationLoadTimeWeaver());
        jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        jpaFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        jpaFactoryBean.setJpaProperties(hibernateProperties());
        return jpaFactoryBean;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource db() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(env.getProperty("jdbc.driver"));
        driver.setUsername(env.getProperty("db.username"));
        driver.setPassword(env.getProperty("db.password"));
        driver.setUrl(env.getProperty("jdbc.url"));
        return driver;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        properties.setProperty("hibernate.cache.provider_class", env.getProperty("hibernate.cache.provider_class"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }
}
