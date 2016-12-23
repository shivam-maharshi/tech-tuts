package org.edu.solr.client;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.SolrPingResponse;

/**
 * The connection manager is required to fetch Solr Client object to communicate
 * with the Solr server for tasks like, creating/updating index and searching on
 * them.
 * 
 * @author shivam.maharshi
 */
public class ConnectionManager {

	private static final String URL_PATTERN = "http://%s:%d/solr/%s";
	private SolrClient client;
	private String url;

	public ConnectionManager(String host, int port, String collection) {
		this.url = String.format(URL_PATTERN, host, port, collection);
		HttpSolrServer server = new HttpSolrServer(url);
		server.setParser(new XMLResponseParser());
		this.client =  new SolrClient(server);
	}

	public SolrClient getConnection() {
		return getConnection(false);
	}
	
	// Pass true if you need new connection.
	public SolrClient getConnection(boolean needNewConn) {
		if(needNewConn)
			return new SolrClient(new HttpSolrServer(this.url));
		return client;
	}
	
	public static boolean verifyConnection(SolrServer client) {
		SolrPingResponse response;
		try {
			response = client.ping();
			return response.getStatus() == 0;
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean verifyConnection() {
		return verifyConnection(this.client.getClient());
	}

}
