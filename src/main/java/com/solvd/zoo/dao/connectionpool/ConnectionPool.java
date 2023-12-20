package com.solvd.zoo.dao.connectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int CONNECTION_POOL_CAPACITY = 5;
    private final Queue<Connection> q = new ConcurrentLinkedQueue<>();
    private static volatile ConnectionPool connectionPoolInstance;
    private static final Properties p = new Properties();
    private static final String url;
    private static final String userName;
    private static final String password;

    static {
        try (FileInputStream f = new FileInputStream("src/main/resources/db.properties")) {
            p.load(f);
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
        url = p.getProperty("db.url");
        userName = p.getProperty("db.username");
        password = p.getProperty("db.password");
    }

    private ConnectionPool() {
        for (int i = 0; i < CONNECTION_POOL_CAPACITY; i++) {
            q.add(getConnectionFromDriverManager());
        }
    }

    public static ConnectionPool getConnectionPool() {
        if (connectionPoolInstance == null) {
            synchronized (ConnectionPool.class) {
                if (connectionPoolInstance == null)
                    connectionPoolInstance = new ConnectionPool();
            }
        }
        return connectionPoolInstance;
    }

    public void releaseConnection(Connection n) {
        synchronized (q) {
            while (q.size() == CONNECTION_POOL_CAPACITY) {
                try {
                    q.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            q.add(n);
            q.notifyAll();
        }
    }

    public Connection getConnection() {
        synchronized (q) {
            while (q.isEmpty()) {
                try {
                    q.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Connection element = q.poll();
            q.notifyAll();
            return element;
        }
    }
    private Connection getConnectionFromDriverManager(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
        return conn;
    }

}
