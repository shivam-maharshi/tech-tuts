package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * The Routing model sender needs to tag every message with a routing key to let
 * the exchanger decide which all queues it should be forwarded to.
 * 
 * @author shivam.maharshi
 */
public class RoutingSender {

	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) throws java.io.IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String message = "The fixed default message";
		String routingKey = "error";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
		System.out.println("[x] Sent " + routingKey + " : '" + message + "'");
		channel.close();
		connection.close();
	}

}
