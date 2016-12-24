package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * A tasks sender that puts the tasks to be performed on the queue. The messages
 * are persistent so they are saved to the disks and the queue is durable which
 * means if the RabbitMQ server goes down still the messages are not lost.
 * 
 * @author shivam.maharshi
 */
public class TaskSender {

	private final static String QUEUE_NAME = "DurableQueue";

	public static void main(String[] argv) throws java.io.IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		boolean durable = true;
		// Queue won't be lost even if RabbitMQ restarts
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		String[] a = { "ye....", "th..ss", ".is.", "very" };
		String message = getMessage(a);
		// Persistent tells RabbitMQ to save the message to disk
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		channel.close();
		connection.close();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "Hello World!..";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}

}
