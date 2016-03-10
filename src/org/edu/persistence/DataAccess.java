package org.edu.persistence;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

/**
 * Performs DB operations for a given cassandra connector the given connection.
 * 
 * @author shivam.maharshi
 */
public class DataAccess {
	CassandraConnector client;

	public DataAccess(String ip, int port) {
		this.client = CassandraConnector.getClient(ip, port);
	}

	/**
	 * Creates keyspace.
	 * 
	 * @param keyspace
	 */
	public void createKeyspace(String keyspace) {
		String createKeyspace = "CREATE KEYSPACE " + keyspace
				+ " WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";
		ResultSet rs = client.getSession().execute(createKeyspace);
		if (rs.isExhausted())
			System.out.println("Keyspace successfully created.");
	}

	/**
	 * Creates table.
	 * 
	 * @param query
	 */
	public void createTable(String query) {
		ResultSet rs = this.client.getSession().execute(query);
		if (rs.isExhausted())
			System.out.println("Table successfully created.");
	}

	/**
	 * Return session.
	 * 
	 * @return
	 */
	public Session getSession() {
		return this.client.getSession();
	}

	public void close() {
		client.close();
	}

}
