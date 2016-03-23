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

public class PubSubReceiver {

	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
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
		System.out.println(" [x] Received '" + message + "'");
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
		writer.write(message);
		writer.flush();
	}

}
