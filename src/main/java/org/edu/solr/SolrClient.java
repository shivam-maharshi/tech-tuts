package org.edu.solr;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
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

	public SolrClient(String host, int port, String collection) {
		this.client = new ConnectionManager(host, port, collection).getConnection();
	}

	public void addDoc(Map<String, String> params) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		for (String key : params.keySet())
			doc.addField(key, params.get(key));
		client.add(doc);
	}

	public void search(Map<String, String> params) throws SolrServerException {
		SolrParams qparams = new MapSolrParams(params);
		QueryResponse qRes = client.query(qparams);
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

}
