package org.edu.solr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;

/**
 * Solr client object is required to operate on the back end indexes. Operations
 * include index creation, modification, deletion, searching etc.
 * 
 * @author shivam.maharshi
 */
public class SolrClient {

	private SolrServer client;

	public SolrClient(SolrServer client) {
		this.client = client;
	}

	public SolrServer getClient() {
		return this.client;
	}

	// Add a document to the Solr index.
	public void addDoc(Map<String, Object> params) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		for (String key : params.keySet())
			doc.addField(key, params.get(key));
		UpdateResponse resp = client.add(doc);
		client.commit();
		if (resp.getStatus() == 0)
			System.out.println("Successfully added document.");
	}

	// Search for a document given the matching parameters.
	public void search(Map<String, String> params) throws SolrServerException {
		// All modification can be made to this object.
		SolrQuery query = new SolrQuery();
		query.setRequestHandler("/select");
		SolrParams qparams = new MapSolrParams(params);
		query.add(qparams);
		// Make query.
		QueryResponse qRes = client.query(qparams, SolrRequest.METHOD.GET);
		SolrDocumentList docList = qRes.getResults();
		Iterator<SolrDocument> it = docList.iterator();
		while (it.hasNext()) {
			SolrDocument doc = it.next();
			Map<String, Object> docMap = doc.getFieldValueMap();
			StringBuilder sb = new StringBuilder();
			sb.append("Result");
			for (String key : docMap.keySet())
				sb.append(" : ").append(key).append(":").append(docMap.get(key));
			System.out.println(sb.toString());
		}
	}

	public static void main(String[] args) throws SolrServerException, IOException {
		ConnectionManager cm = new ConnectionManager("localhost", 8983, "jcg");
		SolrClient client = cm.getConnection();
		System.out.println(" Connection Successful : " + cm.verifyConnection());
		// client.addDoc(getDummyParams());
		client.search(getDummySearchParams());
	}

	private static Map<String, Object> getDummyParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "0553573420");
		params.put("cat", "stuff");
		params.put("name", "The Game of Drones");
		params.put("price", 1000);
		params.put("inStock", true);
		params.put("author", "Sam Mahar");
		params.put("series_t", "A Song of Ice and Fire");
		params.put("sequence_i", 1);
		params.put("genre_s", "real");
		return params;
	}

	private static Map<String, String> getDummySearchParams() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("cat", "book");
		params.put("wt", "json");
		return params;
	}

}
