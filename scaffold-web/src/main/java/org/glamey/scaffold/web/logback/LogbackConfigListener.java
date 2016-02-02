package org.glamey.scaffold.web.logback;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @author zhouyang.zhou
 */
public class LogbackConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogbackWebConfigurer.initLogging(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LogbackWebConfigurer.shutdownLogging(sce.getServletContext());
    }
}
