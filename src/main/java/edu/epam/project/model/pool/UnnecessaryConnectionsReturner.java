package edu.epam.project.model.pool;

class UnnecessaryConnectionsReturner implements Runnable {
    @Override
    public void run() {
        ConnectionPool.getInstance().closeUnnecessaryConnections();
    }
}