package edu.epam.project.pool;

class UnnecessaryConnectionsReturner implements Runnable {
    @Override
    public void run() {
        ConnectionPool.getInstance().closeUnnecessaryConnections();
    }
}