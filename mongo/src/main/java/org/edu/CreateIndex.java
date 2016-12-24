package org.edu;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Create an index on a field for faster search.
 * 
 * @author shivam.maharshi
 */
public class CreateIndex {
	
	public static void main(String args[]) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			// Creates an ascending index on the title field of blog collection.
			db.getCollection("blog").createIndex(new Document("title", 1));
			System.out.println("Index for title field is created.");
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
