package org.edu.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.edu.lucene.utils.IndexUtil;

/**
 * A Lucene indexer that indexes file into hard disk and not RAM.
 * 
 * @author shivam.maharshi
 */
public class FileIndexer implements Indexer {

	private Directory index;

	public FileIndexer(String dir) throws IOException {
		index = FSDirectory.open(new File(dir));
	}

	public void create() throws Exception {
		create(null);
	}

	public void create(Object data) throws Exception {
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, new StandardAnalyzer());
		IndexWriter writer = new IndexWriter(index, config);
		IndexUtil.add(writer, "CS", "BufferedReader improves performance.");
		IndexUtil.add(writer, "Geography", "Global warming isn't a myth.");
		IndexUtil.add(writer, "History", "Columbus was stupid!");
		writer.commit();
		writer.close();
	}

	public Directory get() throws Exception {
		return index;
	}

	public void search(Map<String, String> qparams, List<String> oparams) throws Exception {
		IndexUtil.search(index, qparams, oparams);
	}

	public static void main(String[] args) throws Exception {
		Indexer index = new FileIndexer("C:/Shivam/Work/WorkSpace/LuceneJavaTutorial/index");
		index.create();
		Map<String, String> qparams = new HashMap<String, String>();
		qparams.put("topic", "history");
		List<String> oparams = new ArrayList<String>();
		oparams.add("topic");
		oparams.add("content");
		index.search(qparams, oparams);
	}

}
