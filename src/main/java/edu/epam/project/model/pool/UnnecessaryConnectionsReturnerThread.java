package edu.epam.project.model.pool;

/**
 * UnnecessaryConnectionsReturnerThread thread used for dynamic returning unnecessary connections to pool.
 * @author Sargerion.
 */
class UnnecessaryConnectionsReturnerThread extends Thread {
    @Override
    public void run() {
        ConnectionPool.getInstance().closeUnnecessaryConnections();
    }
}