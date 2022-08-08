package dao.connection;

import java.sql.Connection;


import lombok.extern.log4j.Log4j2;
@Log4j2
public class DataSource implements AutoCloseable {
    public static final DataSource INSTANCE = new DataSource();
    private ConnectionPool connectionPool;
    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    private DataSource() {
        url = ConnectionProperties.INSTANCE.getUrl();
        user = ConnectionProperties.INSTANCE.getUser();
        password = ConnectionProperties.INSTANCE.getPassword();
        driver = ConnectionProperties.INSTANCE.getDriver();
    }

    public Connection getConnection() {
        log.debug("Connection with database");
        if (connectionPool == null) {
            connectionPool = new ConnectionPool(driver, url, user, password);
            log.info("Connection pool inicialized");
        }
        return connectionPool.getConnection();
    }

    ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void close() {
        if (connectionPool != null) {
            connectionPool.destroyPool();
            connectionPool = null;
            log.info("Connection pool destroyed");
        }
    }
}
