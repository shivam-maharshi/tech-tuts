package org.edu.response.provider;

/**
 * Defines response provider contract.
 * 
 * @author shivam.maharshi
 */
public interface ResponseProvider {
	
	public byte[] getResponse(Object request);

}
