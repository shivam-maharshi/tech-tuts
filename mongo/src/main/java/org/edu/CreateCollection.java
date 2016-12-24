package org.edu;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Mongo test client that creates collection.
 * 
 * @author shivam.maharshi
 */
public class CreateCollection {

	public static void main(String args[]) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			db.createCollection("blog");
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
