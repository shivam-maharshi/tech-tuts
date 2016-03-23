package org.edu.models;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Receiver of Pub/Sub model. Create two unique queues on and bind them to the
 * exchange on which the producer is uploading results. The two queues have
 * their consumers here. One is responsible for printing it on console, other
 * one is responsible for writing it onto a log file. Here we are using the
 * fanout exchange. There are four main exchange types - direct, topic, headers
 * & fanout.
 * 
 * @author shivam.maharshi
 */
public class PubSubReceiver {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// We are using the fanout type exchange.
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// Two temporary queues are bound to the exchange.
		// Get unique queue name and bind Console Writer to it.
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		Consumer consoleConsumer = new ConsoleWriterConsumer(channel);
		channel.basicConsume(queueName, true, consoleConsumer);
		// Get unique queue name and bind File Writer to it.
		String fileQueueName = channel.queueDeclare().getQueue();
		channel.queueBind(fileQueueName, EXCHANGE_NAME, "");
		Consumer fileConsumer = new FileWriterConsumer(channel, "C:/Shivam/Work/WorkSpace/RabbitMQTutorial/log.txt");
		channel.basicConsume(queueName, true, fileConsumer);
	}

}

class ConsoleWriterConsumer extends DefaultConsumer {

	public ConsoleWriterConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		String message = new String(body, "UTF-8");
		System.out.println("[x] Received '" + message + "'\n");
		if(envelope.getRoutingKey()!=null)
		System.out.println("[x] Routing key " + envelope.getRoutingKey());
	}

}

class FileWriterConsumer extends DefaultConsumer {
	private Writer writer;

	public FileWriterConsumer(Channel channel, String filepath) {
		super(channel);
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		String message = new String(body, "UTF-8");
		writer.write(message+"\n");
		writer.flush();
	}

}
