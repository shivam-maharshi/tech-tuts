package org.edu.server;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

/**
 * @author shivam.maharshi
 */
public final class WebServer {
	private int port;
	private AsynchronousServerSocketChannel serverChannel;

	public WebServer(int port, int threadpool) throws Exception {
		this.port = port;
	}

	public void start() throws Exception {
		this.serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
		this.serverChannel.accept(serverChannel, new ConnectionHandler());
	}

	public void stop() throws Exception {
		System.out.println("Stopping server.");
		this.serverChannel.close();
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		int threadpool = 5;
		int argLen = args.length;
		for (int i = 0; i < argLen; i++) {
			if (args[i].startsWith("-p=")) {
				port = Integer.valueOf(args[i].split("=")[1]);
			} else if (args[i].startsWith("-t=")) {
				threadpool = Integer.valueOf(args[i].split("=")[1]);
			}
		}
		WebServer server = new WebServer(port, threadpool);
		server.start();
		Thread.sleep(600000000);
	}

}
