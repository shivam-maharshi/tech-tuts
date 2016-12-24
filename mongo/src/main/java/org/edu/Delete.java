package org.edu;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

/**
 * Delete a sample document.
 * 
 * @author shivam.maharshi
 */
public class Delete {
	
	public static void main(String[] args) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			MongoCollection<BasicDBObject> coll = db.getCollection("blog", BasicDBObject.class);
			// Create a sample object.
			coll.insertOne(new BasicDBObject().append("title", "HBase").append("description", "db").append("by", "sam"));
			// Delete this sample object.
			DeleteResult result = coll.deleteOne(Filters.eq("by", "sam"));
			if(result.wasAcknowledged() && result.getDeletedCount()>0)
				System.out.println("Row deleted.");
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
