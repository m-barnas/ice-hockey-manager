package cz.muni.fi.pa165;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@WebListener
public class WebAppInitializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sev) {
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}