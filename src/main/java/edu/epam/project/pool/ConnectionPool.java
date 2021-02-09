package edu.epam.project.pool;

import edu.epam.project.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static final AtomicBoolean isInit = new AtomicBoolean(true);
    private static final int DEFAULT_POOL_SIZE = 16;
    private static final Lock lock_instance = new ReentrantLock();
    private final Lock lock_connection = new ReentrantLock();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        initializePool();
    }

    public static ConnectionPool getInstance() {
        if (isInit.get()) {
            lock_instance.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isInit.set(false);
            }
            lock_instance.unlock();
        }
        return instance;
    }

    private void initializePool() {
        //todo доработать обработку ошибок
        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            ProxyConnection proxyConnection;
            try {
                proxyConnection = new ProxyConnection(connectionBuilder.create());
                freeConnections.offer(proxyConnection);
            } catch (ConnectionException e) {
                logger.error(e);
            }
        }
    }

    public Connection getConnection() throws ConnectionException {
        ProxyConnection proxyConnection;
        try {
            lock_connection.lock();
            proxyConnection = freeConnections.take();
            givenAwayConnections.offer(proxyConnection);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new ConnectionException(e);
        } finally {
            lock_connection.unlock();
        }
        return proxyConnection;
    }

    public void releaseConnection(Connection connection) throws ConnectionException {
        try {
            lock_connection.lock();
            if (!(connection instanceof ProxyConnection)) {
                throw new ConnectionException("It's not a proxy");
            } else {
                if (givenAwayConnections.contains(connection)) {
                    freeConnections.offer((ProxyConnection) connection);
                    givenAwayConnections.remove(connection);
                }
            }
        } finally {
            lock_connection.unlock();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().realClose();
            } catch (SQLException | InterruptedException e) {
                logger.error(e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(e);
            }
        });
    }
}