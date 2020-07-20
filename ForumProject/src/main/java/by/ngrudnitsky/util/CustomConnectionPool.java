package by.ngrudnitsky.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomConnectionPool implements ConnectionPool{
    private final String url;
    private final String user;
    private final String password;
    private List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    private static volatile CustomConnectionPool instance;

    private static final int INITIAL_POOL_SIZE = 10;

    private CustomConnectionPool(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = create();
    }

    private List<Connection> create() {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return pool;
    }

    //todo e
    private static Connection createConnection(String url, String user, String password) {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CustomConnectionPool getConnectionPool(String url, String user, String password){
        if (instance == null) {
            synchronized (CustomConnectionPool.class) {
                if (instance == null) {
                    instance = new CustomConnectionPool(url, user, password);
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
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
}
