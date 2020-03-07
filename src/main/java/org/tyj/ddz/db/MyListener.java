package org.tyj.ddz.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//@WebListener
//@Component
public class MyListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(MyListener.class.getName());

    private SSHConnection conexionssh;

    public MyListener() {
        super();
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Context initialized ... !");
        try {
            conexionssh = new SSHConnection(); // 监听到了 就装配文件
        } catch (Throwable e) {
            logger.error("connect ssh server err！", e);
            e.printStackTrace(); // error connecting SSH server
        }
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Context destroyed ... !");
        conexionssh.closeSSH(); // disconnect
    }

}