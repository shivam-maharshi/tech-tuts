package org.edu.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.SolrPingResponse;

/**
 * A Solr client to communicate with the Solr server for tasks like,
 * creating/updating index and searching on them.
 * 
 * @author shivam.maharshi
 */
public class ConnectionManager {

	private static final String URL_PATTERN = "http://%s:%d/solr/%s";
	private SolrServer client;

	public ConnectionManager(String host, int port, String collection) {
		String url = String.format(URL_PATTERN, host, port, collection);
		this.client = new HttpSolrServer(url);
	}

	public SolrServer getConnection() {
		return this.client;
	}

	public boolean verifyConnection() {
		SolrPingResponse response;
		try {
			response = this.client.ping();
			return response.getStatus() == 200;
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
