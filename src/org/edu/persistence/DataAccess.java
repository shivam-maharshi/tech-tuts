package org.edu.persistence;

import com.datastax.driver.core.ResultSet;

/**
 * Creates a new table with the given connection.
 * 
 * @author shivam.maharshi
 */
public class DataAccess {
	CassandraConnector client;

	public DataAccess(String ip, int port) {
		this.client = CassandraConnector.getClient(ip, port);
	}

	// Creates keyspace.
	public void createKeyspace(String keyspace) {
		String createKeyspace = "CREATE KEYSPACE " + keyspace
				+ " WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";
		ResultSet rs = client.getSession().execute(createKeyspace);
		if (rs.isExhausted())
			System.out.println("Keyspace successfully created.");
	}

	// Creates table.
	public void createTable(String query) {
		ResultSet rs = this.client.getSession().execute(query);
		if (rs.isExhausted())
			System.out.println("Table successfully created.");
	}
	
	public void close() {
		client.close();
	}

	public static void main(String[] args) {
		DataAccess dbo = new DataAccess("127.0.0.1", 9042);
		dbo.createKeyspace("movies_keyspace");
		String createMovieCql = "CREATE TABLE movies_keyspace.movies (title varchar, year int, description varchar, "
				+ "mmpa_rating varchar, dustin_rating varchar, PRIMARY KEY (title, year))";
		dbo.createTable(createMovieCql);
		dbo.close();
	}

}
