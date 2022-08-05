package dao.connection;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.extern.log4j.Log4j2;
import service.impl.UserServiceImpl;
@Log4j2
public class ConnectionProperties {
    public static final ConnectionProperties INSTANCE = new ConnectionProperties();
    private String url;
    private String user;
    private String password;
    private String driver;

    private ConnectionProperties() {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream("/application.properties")) {
            props.load(is);
//            if (System.getenv("local") != null) {
                url = props.getProperty("db.local.url");
                user = props.getProperty("db.local.user");
                password = props.getProperty("db.local.password");
//            } else {
//                url = props.getProperty("db.prod.url");
//                user = props.getProperty("db.prod.user");
//                password = props.getProperty("db.prod.password");
//            }
            driver = props.getProperty("db.driver");
        } catch (Exception e) {
//            logger.error("Error with connection " + e);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

}
