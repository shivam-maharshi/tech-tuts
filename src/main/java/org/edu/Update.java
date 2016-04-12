package org.edu;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * Update an entry.
 * 
 * @author shivam.maharshi
 */
public class Update {

	public static void main(String[] args) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			MongoCollection<Document> coll = db.getCollection("blog");
			FindIterable<Document> findIt = coll.find(Filters.eq("title", "MongoDB"));
			MongoCursor<Document> cursor = findIt.iterator();
			Document doc = null;
			doc = cursor.next();
			// Append field to doc.
			doc.append("NewKey2", "NewValue2");
			// Replaces the complete previous object with this new one.
			// Only the _id is reused.
			coll.replaceOne(Filters.eq("title", "MongoDB"), doc);
			// Updates the document with this id to the new docs value.
			coll.updateOne(Filters.eq("likes", "100"), new Document("title", "Cassandra"));
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
