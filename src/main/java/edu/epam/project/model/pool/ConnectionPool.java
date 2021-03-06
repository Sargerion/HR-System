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

public class ConnectionPool {

    private static ConnectionPool instance;
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isPoolInitialize = new AtomicBoolean(true);
    private static final int MAX_POOL_SIZE = 8;
    private static final int MIN_POOL_SIZE = 4;
    private static final int RETURN_CONNECTION_PERIOD_MINUTES = 2;
    private static final int WAIT_BEFORE_START_REMOVE_CONNECTIONS_MINUTES = 1;
    private static final int CONDITIONAL_VALUE_TO_DOWNSIZE_POOL = 50;
    private static final int HOW_MUCH_DOWNSIZE_POOL = 2;
    private static final Lock lockInstance = new ReentrantLock(true);
    private final Lock lockConnection = new ReentrantLock(true);
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

    public Connection getConnection() throws ConnectionException {
        ProxyConnection proxyConnection = null;
        if (!freeConnections.isEmpty() || detectPoolSize().get() == MAX_POOL_SIZE) {
            try {
                lockConnection.lock();
                proxyConnection = freeConnections.take();
                givenAwayConnections.offer(proxyConnection);
                logger.info("Getting connection from free connections");
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            } finally {
                lockConnection.unlock();
            }
        } else {
            try {
                lockConnection.lock();
                proxyConnection = new ProxyConnection(connectionBuilder.create());
                givenAwayConnections.offer(proxyConnection);
                logger.info("Pool size has increased, current size -> {}", detectPoolSize().get());
                logger.info("New connection created and gone");
            } finally {
                lockConnection.unlock();
            }
        }
        givenPerPeriodConnectionCount.incrementAndGet();
        logger.debug("Connection per period -> {}", givenPerPeriodConnectionCount);
        return proxyConnection;
    }

    public void releaseConnection(Connection connection) throws ConnectionException {
        try {
            lockConnection.lock();
            if (!(connection instanceof ProxyConnection)) {
                logger.error("Not proxy trying release");
                throw new ConnectionException("It's not a proxy");
            } else {
                if (givenAwayConnections.contains(connection)) {
                    freeConnections.offer((ProxyConnection) connection);
                    givenAwayConnections.remove(connection);
                }
            }
        } finally {
            lockConnection.unlock();
        }
    }

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
                //todo спросить про протаскивание пустых пропертей
            }
        }
        UnnecessaryConnectionsReturner connectionsReturner = new UnnecessaryConnectionsReturner();
        scheduledExecutorService.scheduleAtFixedRate(connectionsReturner, WAIT_BEFORE_START_REMOVE_CONNECTIONS_MINUTES, RETURN_CONNECTION_PERIOD_MINUTES, TimeUnit.MINUTES);
    }

    private AtomicInteger detectPoolSize() {
        return new AtomicInteger(freeConnections.size() + givenAwayConnections.size());
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