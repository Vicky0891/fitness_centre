package dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConnectionPool {
    private static final int POOL_SIZE = 30;
    private final BlockingDeque<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenConnection;

    ConnectionPool(String driver, String url, String user, String password) {
        freeConnection = new LinkedBlockingDeque(POOL_SIZE);
        givenConnection = new ArrayDeque();
        try {
            Class.forName(driver);
            log.info("Database driver loaded");
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                freeConnection.offer(new ProxyConnection(connection));
                log.info("Connection created");
            }
        } catch (SQLException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method to get proxy connection
     * 
     * @return Proxy connection
     */
    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenConnection.offer(connection);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Method for release connection and added it to pool
     * 
     * @param connection Connection for release
     */
    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection proxy && givenConnection.remove(connection)) {
            freeConnection.offer(proxy);
        } else {
            log.warn("Returned not proxy connection");
        }
    }

    /**
     * Method to closing connection
     */
    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
                log.info("Connection closed");
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);

            }
        }
        deregisterDrivers();
    }

    /**
     * Method to deregister driver
     */
    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
                log.error("Driver deregistred");
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        });
    }

}
