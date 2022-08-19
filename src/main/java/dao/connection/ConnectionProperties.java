package dao.connection;

import java.io.InputStream;
import java.util.Properties;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class ConnectionProperties {
    public static final ConnectionProperties INSTANCE = new ConnectionProperties();
    public static final String CONFIG_FILE = "/application.properties";
    private String url;
    private String user;
    private String password;
    private String driver;

    private ConnectionProperties() {
        try (InputStream is = getClass().getResourceAsStream(CONFIG_FILE)) {
            Properties props = new Properties();
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
            log.error("Error with connection " + e);
        }
    }

}
