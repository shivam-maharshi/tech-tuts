package org.vt.edu.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author shivam.maharshi
 */
public class RequestHandler implements CompletionHandler<Integer, Connection> {

	@Override
	public void completed(Integer result, Connection connection) {
		AsynchronousSocketChannel client = connection.getClient();
		if (result == -1 || !client.isOpen()) {
			dispose(connection);
			return;
		}

		if (connection.isRead()) {
			handleRequest(connection.getReadWriteBuffer());
			connection.setWrite();
			client.write(connection.getReadWriteBuffer(), connection, this);
		} else {
			connection.setRead();
			connection.getReadWriteBuffer().clear();
			client.read(connection.getReadWriteBuffer(), connection, this);
		}
	}

	@Override
	public void failed(Throwable exc, Connection connection) {
		System.out.println("Client disconnected.");
		ConnectionPool.retrieveConnection(connection);
		exc.printStackTrace();
	}

	private void handleRequest(ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes);
		// Two tasks - Forward HTTP Request & Respond with 204.
		TasksProcessor.forwardHttpRequest(bytes);
		buffer.clear();
		bytes = "HTTP/1.1 204 OK\r\nContent-Length: 0\r\n\r\n".getBytes();
		buffer.put(bytes).flip();
	}

	private void dispose(Connection connection) {
		AsynchronousSocketChannel client = connection.getClient();
		try {
			if (client.isOpen()) {
				System.out.println("Stopped listening to the client " + client.getRemoteAddress());
				client.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		ConnectionPool.retrieveConnection(connection);
	}

}
