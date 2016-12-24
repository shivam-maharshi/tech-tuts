package org.edu.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.edu.lucene.utils.IndexUtil;

/**
 * An example of creating, storing and fetching from an In-Memory Lucene index
 * 
 * @author shivam.maharshi
 */
public class InMemoryIndexer implements Indexer {

	private Directory index;

	public void create() throws IOException {
		create(null);
	}

	public void create(Object data) throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		this.index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		IndexUtil.add(writer, "CS", "BufferedReader improves performance.");
		IndexUtil.add(writer, "Geography", "Global warming isn't a myth.");
		IndexUtil.add(writer, "History", "Columbus was stupid!");
		writer.commit();
		writer.close();
	}

	public void search(Map<String, String> qparams, List<String> oparams) throws IOException, ParseException {
		IndexUtil.search(index, qparams, oparams);
	}

	public static void main(String[] args) throws Exception {
		Indexer index = new InMemoryIndexer();
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
