package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

/**
 * Topics type of exchanges allow a routing much advanced than the Routing type
 * of exchanges. Here the routing key must be in the format *.*.*.* or *.# which
 * can be matched to a particular value at the exchange and will be forwarded to
 * the corresponding listeners whose binding routing key value matches.
 * 
 * @author shivam.maharshi
 */
public class TopicsReceiver {
	
	private static final String EXCHANGE_NAME = "topics_logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// We are using the topic type exchange.
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		// Two temporary queues are bound to the exchange.
		// Get unique queue name and bind Console Writer to it.
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "warn.*");
		channel.queueBind(queueName, EXCHANGE_NAME, "*.verbose");
		Consumer consoleConsumer = new ConsoleWriterConsumer(channel);
		channel.basicConsume(queueName, true, consoleConsumer);
		// Get unique queue name and bind File Writer to it.
		String fileQueueName = channel.queueDeclare().getQueue();
		channel.queueBind(fileQueueName, EXCHANGE_NAME, "error.minimal");
		Consumer fileConsumer = new FileWriterConsumer(channel, "C:/Shivam/Work/WorkSpace/RabbitMQTutorial/log.txt");
		channel.basicConsume(queueName, true, fileConsumer);
	}

}
