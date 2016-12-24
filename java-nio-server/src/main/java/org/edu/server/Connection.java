package org.edu.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Objects;

/**
 * @author shivam.maharshi
 */
public final class Connection {
    private AsynchronousSocketChannel client;
    private boolean isRead;
    private final ByteBuffer readWriteBuffer;

    Connection(final AsynchronousSocketChannel client) {
        Objects.requireNonNull(client);
        this.client = client;
        this.readWriteBuffer = ByteBuffer.allocateDirect(2 * 1024);
        this.isRead = true;
    }

    void setSocketAddress(AsynchronousSocketChannel newClient) {
        this.client = newClient;
    }

    public AsynchronousSocketChannel getClient() {
        return client;
    }

    public ByteBuffer getReadWriteBuffer() {
        return readWriteBuffer;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead() {
        isRead = true;
    }

    public void setWrite() {
        isRead = false;
    }
}
