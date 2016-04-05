package org.edu.server;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shivam.maharshi
 */
public final class ConnectionPool {
    private static ConcurrentLinkedQueue<Connection> pool = new ConcurrentLinkedQueue<>();

    public static Connection getConnection(final AsynchronousSocketChannel forClient) {
        Objects.requireNonNull(forClient);
        Connection poll = pool.poll();
        if (poll != null)
            poll.setSocketAddress(forClient);
        else
            poll = new Connection(forClient);
        return poll;
    }

    public static void retrieveConnection(final Connection connection) {
        Objects.requireNonNull(connection);
        connection.setSocketAddress(null);
        connection.setRead();
        connection.getReadWriteBuffer().clear();
        pool.offer(connection);
    }
}
