package org.edu.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
	private SolrServer client;
	private String url;

	public ConnectionManager(String host, int port, String collection) {
		this.url = String.format(URL_PATTERN, host, port, collection);
		this.client = new HttpSolrServer(url);
	}

	public SolrServer getConnection() {
		return getConnection(false);
	}
	
	// Pass true if you need new connection.
	public SolrServer getConnection(boolean needNewConn) {
		if(needNewConn)
			return new HttpSolrServer(this.url);
		return client;
	}
	
	public boolean verifyConnection(SolrServer client) {
		SolrPingResponse response;
		try {
			response = client.ping();
			return response.getStatus() == 200;
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean verifyConnection() {
		return verifyConnection(this.client);
	}

}
