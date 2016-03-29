package org.vt.edu.server;

/**
 * Performs the tasks with the input.
 * 
 * @author shivam.maharshi
 */
public class TasksProcessor {
	
	public static void forwardHttpRequest(byte[] bytes) {
		System.out.println("The incoming data: \n" + new String(bytes));
	}

}
