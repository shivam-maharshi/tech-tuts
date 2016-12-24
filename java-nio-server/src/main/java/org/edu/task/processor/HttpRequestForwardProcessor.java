package org.edu.task.processor;

/**
 * Task processor that simply forward the incoming request to another server. In
 * our use case it was a RabbitMQ Server.
 * 
 * @author shivam.maharshi
 */
public class HttpRequestForwardProcessor implements TasksProcessor {

	@Override
	public void process(byte[] bytes) {
		System.out.println("The incoming data: \n" + new String(bytes));
	}

}
