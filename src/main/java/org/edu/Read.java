package org.edu;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Read operation.
 * 
 * @author shivam.maharshi
 */
public class Read {

	public static void main(String[] args) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			MongoCollection<Document> coll = db.getCollection("blog");
			FindIterable<Document> findIt = coll.find();
			MongoCursor<Document> cursor = findIt.iterator();
			int i = 0;
			while(cursor.hasNext()) {
				System.out.println("Inserted document: "+i);
				System.out.println("Cursor: "+cursor.next());
				i++;
			}
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
