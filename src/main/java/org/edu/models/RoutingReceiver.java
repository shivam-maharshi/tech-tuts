package org.edu.models;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

/**
 * This is a special case of the Pub/Sub model type. Here instead of fanout type
 * of exchange, we use direct type of exchange. Here producer tags every message
 * with a routing key. On the basis of this routing key, the exchange decides
 * which message needs to go to which queue. In this example the routing key
 * would be the severity of the log message, like warn, error. If the message is
 * warn it will be only displayed on the console. However if the message is
 * error, then it will be displayed on the console and written to the file as
 * well. This can save diskspace.
 * 
 * @author shivam.maharshi
 */
public class RoutingReceiver {

	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// We are using the fanout type exchange.
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// Two temporary queues are bound to the exchange.
		// Get unique queue name and bind Console Writer to it.
		String queueName = channel.queueDeclare().getQueue();
		// Write messages to console with severity warn or error.
		channel.queueBind(queueName, EXCHANGE_NAME, "warn");
		channel.queueBind(queueName, EXCHANGE_NAME, "error");
		Consumer consoleConsumer = new ConsoleWriterConsumer(channel);
		channel.basicConsume(queueName, true, consoleConsumer);
		// Get unique queue name and bind File Writer to it.
		String fileQueueName = channel.queueDeclare().getQueue();
		// Only write messages to file with severity error.
		channel.queueBind(fileQueueName, EXCHANGE_NAME, "error");
		Consumer fileConsumer = new FileWriterConsumer(channel, "C:/Shivam/Work/WorkSpace/RabbitMQTutorial/log.txt");
		channel.basicConsume(queueName, true, fileConsumer);
	}

}
