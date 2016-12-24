package org.edu.server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author shivam.maharshi
 */
public class ConnectionHandler
		implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {
	
	@Override
	public void completed(AsynchronousSocketChannel client, AsynchronousServerSocketChannel server) {
		try {
			SocketAddress clientAddr = client.getRemoteAddress();
			System.out.format("Accepted a connection from %s%n", clientAddr);
			//server.accept(server, this);
			Connection connection = ConnectionPool.getConnection(client);
			client.read(connection.getReadWriteBuffer(), connection, new RequestHandler());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void failed(Throwable e, AsynchronousServerSocketChannel server) {
		if (server.isOpen()) {
			System.out.println("Failed to accept a connection.");
			e.printStackTrace();
		}
	}
}