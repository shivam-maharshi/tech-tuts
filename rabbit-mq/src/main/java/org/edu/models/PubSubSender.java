package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * The Broadcast or the Publisher/Subscriber model. One sender and multiple
 * receiver. This class sends a message to an exchange as opposed to a queue,
 * which was the case in the previous two models - Simple & Worker model.
 * 
 * @author shivam.maharshi
 */
public class PubSubSender {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] args) throws java.io.IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String message = "The fixed default message";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}

}
