package org.edu.response.provider;

/**
 * Provides no content response. Can be used for faster acknowledgments.
 * 
 * @author shivam.maharshi
 */
public class NoContentProvider implements ResponseProvider {

	@Override
	public byte[] getResponse(Object request) {
		return "HTTP/1.1 204 OK\r\nContent-Length: 0\r\n\r\n".getBytes();
	}

}
