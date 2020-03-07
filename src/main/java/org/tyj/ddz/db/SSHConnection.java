package org.tyj.ddz.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SSHConnection {

    private static final Logger logger = LoggerFactory.getLogger(SSHConnection.class.getName());

    private final static int LOCAl_PORT = 3307;
    private final static int REMOTE_PORT = 3306;
    private final static int SSH_REMOTE_PORT = 22;
    private final static String SSH_USER = "root";
    private final static String SSH_PASSWORD = "xjgdjkys@2019@)!(";
    private final static String MYSQL_REMOTE_SERVER = "127.0.0.1";
    private final static String SSH_REMOTE_SERVER = "39.97.243.5";
    private Session sesion; //represents each ssh session

    public void closeSSH() {
        sesion.disconnect();
    }

    public SSHConnection() throws Throwable {
        try {
            JSch jsch = null;
            jsch = new JSch();

            sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
            sesion.setPassword(SSH_PASSWORD);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            sesion.setConfig(config);

            sesion.connect(); //ssh connection established!
            logger.info(sesion.getServerVersion());//这里打印SSH服务器版本信息

            //by security policy, you must connect through a fowarded port
            sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
        } catch (Exception e) {
            logger.error("create database ssh connection error！", e);
        }

    }
}