package org.edu.lucene.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

/**
 * A common function to be used by multiple indexer class implementations.
 * 
 * @author shivam.maharshi
 */
public class IndexUtil {

	public static void add(IndexWriter writer, String topic, String content) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("topic", topic, Store.YES));
		doc.add(new TextField("content", content, Store.YES));
		writer.addDocument(doc);
	}

	/**
	 * Prints the documents after running the query on the index passed as the
	 * argument variables.
	 * 
	 * @param index
	 * @param qparams
	 *            - are the key value parameters which make up the query to be
	 *            run.
	 * @param oparams
	 *            - are the parameters to be shown in the output.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void search(Directory index, Map<String, String> qparams, List<String> oparams)
			throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		for (String key : qparams.keySet()) {
			Query query = new QueryParser(key, new StandardAnalyzer()).parse(qparams.get(key));
			TopDocs result = searcher.search(query, 10);
			ScoreDoc[] hits = result.scoreDocs;
			System.out.println(String.format("Found %s hits.", hits.length));
			for (ScoreDoc hit : hits) {
				Document document = searcher.doc(hit.doc);
				StringBuilder sb = new StringBuilder("Result");
				for (String o : oparams)
					sb.append(" | ").append(o).append(":").append(document.get(o));
				System.out.println(sb.toString());
			}
		}
	}

}
