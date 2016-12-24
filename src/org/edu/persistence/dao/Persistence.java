package org.edu.persistence.dao;

import java.util.Optional;

import com.datastax.driver.core.ResultSet;

/**
 * Defines the functions that must be implemented by a persistence object. These
 * are CRUD.
 * 
 * @author shivam.maharshi
 */
public interface Persistence<T> {

	public ResultSet create(T o);
	
	public Optional<T> read(T o);
	
	public ResultSet update(T o);
	
	public ResultSet delete(T o);
	
}
