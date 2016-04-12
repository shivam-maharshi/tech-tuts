package org.edu;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Insert a sample document.
 * 
 * @author shivam.maharshi
 */
public class Insert {

	public static void main(String[] args) {
		try {
			MongoClient client = new MongoClient("127.0.0.1", 27017);
			MongoDatabase db = client.getDatabase("local");
			System.out.println("Connect to database successfully");
			MongoCollection<Document> coll = db.getCollection("blog");
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("title", "MongoDB");
			map.put("description", "repository");
			map.put("likes", 150);
			map.put("url", "http://www.github.com/shivam-maharshi");
			map.put("by", "GitHub");
			Document doc = new Document(map);
			coll.insertOne(doc);
			client.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
