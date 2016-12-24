package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Sends messages with various topics to the receiver. Uses topics type of
 * exchange as opposed to the routing one.
 * 
 * @author shivam.maharshi
 */
public class TopicsSender {

	private static final String EXCHANGE_NAME = "topics_logs";

	public static void main(String[] args) throws java.io.IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String message = "The fixed default message";
		channel.basicPublish(EXCHANGE_NAME, getRoutingKeyForAll(), null, message.getBytes());
		channel.basicPublish(EXCHANGE_NAME, getRoutingKeyForErrorsAndMinimalLogging(), null, message.getBytes());
		channel.basicPublish(EXCHANGE_NAME, getRoutingKeyForWarnsAndAllLogging(), null, message.getBytes());
		System.out.println("[x] Sent " + message + "'");
		channel.close();
		connection.close();
	}

	private static String getRoutingKeyForAll() {
		return "*.*";
	}

	private static String getRoutingKeyForErrorsAndMinimalLogging() {
		return "error.minimal";
	}

	private static String getRoutingKeyForWarnsAndAllLogging() {
		return "warn.minimal";
	}

}
