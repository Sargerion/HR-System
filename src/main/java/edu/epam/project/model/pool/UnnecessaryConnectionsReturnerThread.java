package edu.epam.project.model.pool;

class UnnecessaryConnectionsReturnerThread extends Thread {
    @Override
    public void run() {
        ConnectionPool.getInstance().closeUnnecessaryConnections();
    }
}