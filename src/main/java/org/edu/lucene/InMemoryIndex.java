package org.edu.lucene;

import java.io.IOException;

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
public class InMemoryIndex {
	
	public static Directory createIndex() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		IndexWriter writer = new IndexWriter(index, config);
		add(writer, "CS", "BufferedReader improves performance.");
		add(writer, "Geography", "Global warming isn't a myth.");
		add(writer, "History", "Columbus was stupid!");
		writer.commit();
		writer.close();
		return index;
	}
	
	public static void query(Directory index, Query query) throws IOException {
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs resultDocs = searcher.search(query, 10);
		int totalHits = resultDocs.totalHits;
		ScoreDoc[] docs = resultDocs.scoreDocs;
		System.out.println("Total Search Hits: "+totalHits);
		for(ScoreDoc doc : docs) {
			Document d = searcher.doc(doc.doc);
			System.out.println("Topic: "+d.get("topic") + " | Content: "+ d.get("content"));
		}
	}
	
	public static void add(IndexWriter writer, String topic, String content) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("topic", topic, Store.YES));
		doc.add(new TextField("content", content, Store.YES));
		writer.addDocument(doc);
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		Directory index = createIndex();
		Query query = new QueryParser("topic", new StandardAnalyzer()).parse("History");
		query(index, query);
		query = new QueryParser("content", new StandardAnalyzer()).parse("myth");
		query(index, query);
	}

}
