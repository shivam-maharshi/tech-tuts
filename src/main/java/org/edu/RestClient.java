package org.edu;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Class responsible for making web service requests using Apache HttpClient
 * over standard Java HTTP API as this is more flexible and provides better
 * functionality. For example HttpClient can automatically handle Redirects and
 * Proxy Authentication which the standard Java API don't.
 * 
 * @author shivam.maharshi
 */
public class RestClient {

	private static boolean logEnabled = true;
	private static boolean compressedResponse = false;
	private String urlPrefix = "http://127.0.0.1:%d/%s";
	private CloseableHttpClient client;
	private String[] headers;
	private static int conTimeout = 10000;
	private static int readTimeout = 10000;
	private static int execTimeout = 10000;
	public volatile Criteria requestTimedout = new Criteria(false);

	public RestClient(int port, String endpoint) {
		super();
		urlPrefix = String.format(urlPrefix, port, endpoint);
		setupClient();
	}

	private void setupClient() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(conTimeout);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(readTimeout);
		requestBuilder = requestBuilder.setSocketTimeout(readTimeout);
		HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build());
		headers = new String[] { "Accept", "*/*", "Accept-Language", "en-US,en;q=0.5", "Content-Type",
				"application/x-www-form-urlencoded", "user-agent", "Mozilla/5.0" };
		this.client = clientBuilder.setUserAgent("Mozilla/5.0").setConnectionManagerShared(true).build();
	}

	public Status read(String table, String endpoint, Set<String> fields, HashMap<String, String> result) {
		int responseCode;
		try {
			responseCode = httpGet(urlPrefix + endpoint, result);
		} catch (Exception e) {
			responseCode = handleExceptions(e);
		}
		if (logEnabled)
			System.out.println("GET Request: " + urlPrefix + endpoint + " | Response Code: " + responseCode);
		return getStatus(responseCode);
	}

	public Status insert(String table, String endpoint, HashMap<String, String> values) {
		int responseCode;
		try {
			responseCode = httpPost(urlPrefix + endpoint, values.get("data").toString());
		} catch (Exception e) {
			responseCode = handleExceptions(e);
		}
		if (logEnabled)
			System.out.println("POST Request: " + urlPrefix + endpoint + " | Response Code: " + responseCode);
		return getStatus(responseCode);
	}

	public Status delete(String table, String endpoint) {
		int responseCode;
		try {
			responseCode = httpDelete(urlPrefix + endpoint);
		} catch (Exception e) {
			responseCode = handleExceptions(e);
		}
		if (logEnabled)
			System.out.println("DELETE Request: " + urlPrefix + endpoint + " | Response Code: " + responseCode);
		return getStatus(responseCode);
	}

	public Status update(String table, String key, HashMap<String, String> values) {
		System.out.println("Update not implemented.");
		return Status.OK;
	}

	public Status scan(String table, String startkey, int recordcount, Set<String> fields,
			Vector<HashMap<String, String>> result) {
		System.out.println("Scan operation is not supported for RESTFul Web Services client.");
		return Status.OK;
	}

	private Status getStatus(int responseCode) {
		if (responseCode / 100 == 5)
			return Status.ERROR;
		return Status.OK;
	}

	private int handleExceptions(Exception e) {
		e.printStackTrace();
		if (e instanceof ClientProtocolException)
			return 400;
		return 500;
	}

	// Connection is automatically released back in case of an exception.
	private int httpGet(String endpoint, HashMap<String, String> result) throws IOException, TimeoutException {
		requestTimedout.setSatisfied(false);
		Thread timer = new Thread(new Timer(execTimeout, requestTimedout));
		timer.start();
		int responseCode = 200;
		HttpGet request = new HttpGet(endpoint);
		for (int i = 0; i < headers.length; i = i + 2)
			request.setHeader(headers[i], headers[i + 1]);
		CloseableHttpResponse response = client.execute(request);
		responseCode = response.getStatusLine().getStatusCode();
		HttpEntity responseEntity = response.getEntity();
		// If null entity don't bother about connection release.
		if (responseEntity != null) {
			InputStream stream = responseEntity.getContent();
			if (compressedResponse)
				stream = new GZIPInputStream(stream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer responseContent = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (requestTimedout.isSatisfied()) {
					// Must avoid memory leak.
					reader.close();
					stream.close();
					EntityUtils.consumeQuietly(responseEntity);
					response.close();
					client.close();
					throw new TimeoutException();
				}
				responseContent.append(line);
			}
			timer.interrupt();
			result.put("response", responseContent.toString());
			// Closing the input stream will trigger connection release.
			stream.close();
		}
		EntityUtils.consumeQuietly(responseEntity);
		response.close();
		client.close();
		return responseCode;
	}

	// Connection is automatically released back in case of an exception.
	private int httpPost(String endpoint, String postData) throws IOException, TimeoutException {
		requestTimedout.setSatisfied(false);
		Thread timer = new Thread(new Timer(execTimeout, requestTimedout));
		timer.start();
		int responseCode = 200;
		HttpPost request = new HttpPost(endpoint);
		for (int i = 0; i < headers.length; i = i + 2)
			request.setHeader(headers[i], headers[i + 1]);
		InputStreamEntity reqEntity = new InputStreamEntity(new ByteArrayInputStream(postData.getBytes()),
				ContentType.APPLICATION_FORM_URLENCODED);
		reqEntity.setChunked(true);
		request.setEntity(reqEntity);
		CloseableHttpResponse response = client.execute(request);
		responseCode = response.getStatusLine().getStatusCode();
		HttpEntity responseEntity = response.getEntity();
		// If null entity don't bother about connection release.
		if (responseEntity != null) {
			InputStream stream = responseEntity.getContent();
			if (compressedResponse)
				stream = new GZIPInputStream(stream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuffer responseContent = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (requestTimedout.isSatisfied()) {
					// Must avoid memory leak.
					reader.close();
					stream.close();
					EntityUtils.consumeQuietly(responseEntity);
					response.close();
					client.close();
					throw new TimeoutException();
				}
				responseContent.append(line);
			}
			timer.interrupt();
			// Closing the input stream will trigger connection release.
			stream.close();
		}
		EntityUtils.consumeQuietly(responseEntity);
		response.close();
		client.close();
		return responseCode;
	}

	// Connection is automatically released back in case of an exception.
	private int httpDelete(String endpoint) throws IOException {
		int responseCode = 200;
		HttpDelete request = new HttpDelete(endpoint);
		CloseableHttpResponse response = client.execute(request);
		responseCode = response.getStatusLine().getStatusCode();
		if (response != null)
			response.close();
		client.close();
		return responseCode;
	}

	class Timer implements Runnable {

		private long timeout;
		private Criteria timedout;

		public Timer(long timeout, Criteria timedout) {
			this.timedout = timedout;
			this.timeout = timeout;
		}

		public void run() {
			try {
				Thread.sleep(timeout);
				this.timedout.setSatisfied(true);
			} catch (InterruptedException e) {
				// Do nothing.
			}
		}

	}

	enum Status {
		OK, ERROR;
	}

	class Criteria {

		private boolean isSatisfied;

		public Criteria(boolean isSatisfied) {
			this.isSatisfied = isSatisfied;
		}

		public boolean isSatisfied() {
			return isSatisfied;
		}

		public void setSatisfied(boolean isSatisfied) {
			this.isSatisfied = isSatisfied;
		}

	}

	class TimeoutException extends RuntimeException {

		private static final long serialVersionUID = 1L;

	}

}