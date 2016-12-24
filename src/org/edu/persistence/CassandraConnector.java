package org.edu.persistence;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

/**
 * Class used for connecting to Cassandra database.
 * 
 * @author shivam.maharshi
 */
public class CassandraConnector {
	
	private Cluster cluster;
	private Session session;

	/**
	 * Connect to Cassandra Cluster specified by provided node IP address and
	 * port number.
	 *
	 * @param node
	 *            Cluster node IP address.
	 * @param port
	 *            Port of cluster host.
	 */
	public void connect(final String node, final int port) {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
		final Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}
		session = cluster.connect();
	}

	/**
	 * Provide my Session.
	 *
	 * @return My session.
	 */
	public Session getSession() {
		return this.session;
	}

	/** Close cluster. */
	public void close() {
		cluster.close();
	}

	public static CassandraConnector getClient(String ip, int port) {
		CassandraConnector client = new CassandraConnector();
		System.out.println("Connecting to IP Address " + ip + ":" + port + "...");
		client.connect(ip, port);
		return client;
	}

}