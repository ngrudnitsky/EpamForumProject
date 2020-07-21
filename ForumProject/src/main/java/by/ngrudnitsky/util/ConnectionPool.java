package by.ngrudnitsky.util;

import by.ngrudnitsky.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static String url;
    private static String username;
    private static String password;
    private static int initPoolSize;

    private static List<Connection> connectionPool = new ArrayList<>();
    private static final List<Connection> usedConnections = new ArrayList<>();

    private ConnectionPool() {
    }

    private static void create() throws SQLException {
        connectionPool = new ArrayList<>(initPoolSize);
        for (int i = 0; i < initPoolSize; i++) {
            connectionPool.add(createConnection());
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void initConnectionPool(String url, String username, String password, String initPoolSize) throws ConnectionException {
        try {
            ConnectionPool.url = url;
            ConnectionPool.username = username;
            ConnectionPool.password = password;
            ConnectionPool.initPoolSize = Integer.parseInt(initPoolSize);
            create();
        } catch (SQLException e) {
            throw new ConnectionException("Invalid connection properties", e);
        }
    }

    public static Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }


    public static void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }
}
