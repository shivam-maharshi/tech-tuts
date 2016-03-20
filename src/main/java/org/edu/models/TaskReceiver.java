package org.edu.models;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * A sample Receiver class that consumes messages from queue.
 * 
 * @author shivam.maharshi
 */
public class TaskReceiver {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer = new TaskConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}

class TaskConsumer extends DefaultConsumer {

	public TaskConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException {
		String message = new String(body, "UTF-8");
		System.out.println(" [x] Received '" + message + "'");
		try {
			doWork(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println(" [x] Done for message: " + message);
		}
	}

	private static void doWork(String task) throws InterruptedException {
		for (char ch : task.toCharArray()) {
			if (ch == '.')
				Thread.sleep(1000);
		}
	}

}
