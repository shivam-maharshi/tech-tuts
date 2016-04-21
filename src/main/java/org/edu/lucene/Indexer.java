package org.edu.lucene;

import java.util.List;
import java.util.Map;

import org.apache.lucene.store.Directory;

/**
 * A common interface that defines the common functionalities that every index
 * type must implement.
 * 
 * @author shivam.maharshi
 */
public interface Indexer {
	
	public void create() throws Exception;
	
	public void create(Object data) throws Exception;
	
	public Directory get() throws Exception;
	
	public void search(Map<String, String> qparams, List<String> oparams) throws Exception;

}
