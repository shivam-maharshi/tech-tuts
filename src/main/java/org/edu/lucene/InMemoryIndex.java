package org.edu.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * An example of creating, storing and fetching from an In-Memory Lucene index
 * 
 * @author shivam.maharshi
 */
public class InMemoryIndex implements Index {

	Directory index;

	public void create() throws IOException {
		create(null);
	}

	public void create(Object data) throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		this.index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		add(writer, "CS", "BufferedReader improves performance.");
		add(writer, "Geography", "Global warming isn't a myth.");
		add(writer, "History", "Columbus was stupid!");
		writer.commit();
		writer.close();
	}

	public void search(Map<String, String> qparams, List<String> oparams) throws IOException, ParseException {
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
				for(String o: oparams)
				sb.append(" | ").append(o).append(":").append(document.get(o));
				System.out.println(sb.toString());
			}
		}
	}

	public static void add(IndexWriter writer, String topic, String content) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("topic", topic, Store.YES));
		doc.add(new TextField("content", content, Store.YES));
		writer.addDocument(doc);
	}

	public static void main(String[] args) throws Exception {
		Index index = new InMemoryIndex();
		index.create();
		Map<String, String> qparams = new HashMap<String, String>();
		qparams.put("topic", "history");
		List<String> oparams = new ArrayList<String>();
		oparams.add("topic");
		oparams.add("content");
		index.search(qparams, oparams);
	}

	public Directory get() throws IOException {
		return index;
	}

}
