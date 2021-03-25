package edu.epam.project.model.pool;

import edu.epam.project.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Thread-safe, dynamic ConnectionPool to optimize the work with the database.
 * @author Sargerion.
 */
public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance;
    private static final AtomicBoolean isPoolInitialize = new AtomicBoolean(true);
    private static final int MAX_POOL_SIZE = 8;
    private static final int MIN_POOL_SIZE = 4;
    private static final int RETURN_CONNECTION_PERIOD_MINUTES = 1;
    private static final int WAIT_BEFORE_START_REMOVE_CONNECTIONS_MINUTES = 1;
    private static final int CONDITIONAL_VALUE_TO_DOWNSIZE_POOL = 50;
    private static final int HOW_MUCH_DOWNSIZE_POOL = 2;
    private static final Lock lockInstance = new ReentrantLock(true);
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnections;
    private final ConnectionBuilder connectionBuilder = new ConnectionBuilder();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private AtomicInteger givenPerPeriodConnectionCount = new AtomicInteger(0);

    private ConnectionPool() {
        freeConnections = new LinkedBlockingDeque<>(MAX_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>(MAX_POOL_SIZE);
        initializePool();
    }

    /**
     * Gets instance with double-checked locking.
     *
     * @return the instance of ConnectionPool object.
     */
    public static ConnectionPool getInstance() {
        if (isPoolInitialize.get()) {
            lockInstance.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isPoolInitialize.set(false);
            }
            lockInstance.unlock();
        }
        return instance;
    }

    /**
     * Gets connection from pool.
     *
     * @return the Connection object.
     * @throws ConnectionException if InterruptedException was thrown.
     */
    public Connection getConnection() throws ConnectionException {
        ProxyConnection proxyConnection = null;
        if (!freeConnections.isEmpty() || detectPoolSize().get() == MAX_POOL_SIZE) {
            try {
                proxyConnection = freeConnections.take();
                givenAwayConnections.offer(proxyConnection);
                logger.info("Getting connection from free connections");
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            }
        } else {
            proxyConnection = new ProxyConnection(connectionBuilder.create());
            givenAwayConnections.offer(proxyConnection);
            logger.info("Pool size has increased, current size -> {}", detectPoolSize().get());
            logger.info("New connection created and gone");
        }
        givenPerPeriodConnectionCount.incrementAndGet();
        logger.debug("Connection per period -> {}", givenPerPeriodConnectionCount);
        return proxyConnection;
    }

    /**
     * Release connection.
     *
     * @param connection the Connection object.
     * @throws ConnectionException if trying to release not ProxyConnection object.
     */
    public void releaseConnection(Connection connection) throws ConnectionException {
        if (!(connection instanceof ProxyConnection)) {
            logger.error("Not proxy trying release");
            throw new ConnectionException("It's not a proxy");
        } else {
            if (givenAwayConnections.contains(connection)) {
                freeConnections.offer((ProxyConnection) connection);
                givenAwayConnections.remove(connection);
            }
        }
    }

    /**
     * Destroy pool, which called before program finally finished.
     *
     * @throws ConnectionException if InterruptedException or SQLException was thrown.
     */
    public void destroyPool() throws ConnectionException {
        for (int i = 0; i < detectPoolSize().get(); i++) {
            try {
                freeConnections.take().realClose();
            } catch (SQLException e) {
                logger.error(e);
                throw new ConnectionException(e);
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    /**
     * Close unnecessary connections.
     * Called by UnnecessaryConnectionsReturnerThread in override method run() for dynamic decreasing pool size.
     */
    void closeUnnecessaryConnections() {
        if (givenPerPeriodConnectionCount.get() < CONDITIONAL_VALUE_TO_DOWNSIZE_POOL) {
            int connectionsToCloseCount = HOW_MUCH_DOWNSIZE_POOL;
            while (!freeConnections.isEmpty() && connectionsToCloseCount != 0) {
                try {
                    freeConnections.take().realClose();
                    connectionsToCloseCount--;
                } catch (SQLException e) {
                    logger.error(e);
                } catch (InterruptedException e) {
                    logger.error(e);
                    Thread.currentThread().interrupt();
                }
            }
        }
        givenPerPeriodConnectionCount.set(0);
        logger.info("Pool size decreased by -> {}, current pool size -> {}", HOW_MUCH_DOWNSIZE_POOL, detectPoolSize());
    }

    private void initializePool() {
        for (int i = 0; i < MIN_POOL_SIZE; i++) {
            ProxyConnection proxyConnection;
            try {
                proxyConnection = new ProxyConnection(connectionBuilder.create());
                freeConnections.offer(proxyConnection);
            } catch (ConnectionException e) {
                logger.fatal(e);
                throw new RuntimeException(e);
            }
        }
        UnnecessaryConnectionsReturnerThread connectionsReturner = new UnnecessaryConnectionsReturnerThread();
        scheduledExecutorService.scheduleAtFixedRate(connectionsReturner, WAIT_BEFORE_START_REMOVE_CONNECTIONS_MINUTES, RETURN_CONNECTION_PERIOD_MINUTES, TimeUnit.MINUTES);
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

    private AtomicInteger detectPoolSize() {
        return new AtomicInteger(freeConnections.size() + givenAwayConnections.size());
    }
}