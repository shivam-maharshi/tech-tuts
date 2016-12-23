package org.edu;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.edu.RestClient.Status;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

/**
 * Mocking REST services with the easiest and the most straight forward usage
 * using stubbing. Performs these steps in order. 1. Mocks the web server part
 * of the rest services. 2. Invokes the rest client to perform HTTP operations
 * on the server. 3. Compares the response received with the expected response.
 * Various cases like - success, failure, timeout, non-implemented etc have been
 * demonstrated.
 * 
 * @author shivam.maharshi
 */
public class WebServiceMock {

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(
			WireMockConfiguration.wireMockConfig().port(8080));

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;

	private RestClient rc = new RestClient(8080, "myService/");
	private static final String RESPONSE_TAG = "response";
	private static final String DATA_TAG = "data";
	private static final String SUCCESS_RESPONSE = "<response>success</response>";
	private static final String INPUT_DATA = "<field1>one</field1><field2>two</field2>";
	private static final int RESPONSE_DELAY = 11000; // 11 seconds.
	private static final String resource = "1";

	@BeforeClass
	public static void startServer() {
		wireMockRule.start();
	}

	@AfterClass
	public static void stopServer() {
		wireMockRule.shutdownServer();
	}

	@Test
	// Successfully read data.
	public void read200() {
		stubFor(get(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "text/xml").withBody(SUCCESS_RESPONSE)));
		HashMap<String, String> result = new HashMap<String, String>();
		Status status = rc.read(resource, result);
		assertEquals(Status.OK, status);
		assertEquals(result.get(RESPONSE_TAG), SUCCESS_RESPONSE);
	}

	@Test
	// Resource not found.
	public void read400() {
		stubFor(get(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*")).willReturn(
				aResponse().withStatus(404).withHeader("Content-Type", "text/xml").withBody(SUCCESS_RESPONSE)));
		HashMap<String, String> result = new HashMap<String, String>();
		Status status = rc.read(resource, result);
		assertEquals(Status.NOT_FOUND, status);
		assertEquals(result.get(RESPONSE_TAG), SUCCESS_RESPONSE);
	}

	@Test
	// Successfully insert data.
	public void insert200() {
		stubFor(post(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*"))
				.withRequestBody(equalTo(INPUT_DATA)).willReturn(
						aResponse().withStatus(200).withHeader("Content-Type", "text/xml").withBody(SUCCESS_RESPONSE)));
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(DATA_TAG, INPUT_DATA);
		Status status = rc.insert(resource, data);
		assertEquals(Status.OK, status);
	}

	@Test
	// Response delay will trigger timeout/socket exception.
	public void insert500() {
		stubFor(post(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*"))
				.withRequestBody(equalTo(INPUT_DATA))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "text/xml")
						.withBody(SUCCESS_RESPONSE).withFixedDelay(RESPONSE_DELAY)));
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(DATA_TAG, INPUT_DATA);
		Status status = rc.insert(resource, data);
		assertEquals(Status.ERROR, status);
	}

	@Test
	// Successful delete.
	public void delete200() {
		stubFor(delete(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "text/xml").withBody(SUCCESS_RESPONSE)));
		Status status = rc.delete(resource);
		assertEquals(Status.OK, status);
	}

	@Test
	// Non-implemented update.
	public void update() {
		assertEquals(Status.NOT_IMPLEMENTED, rc.update(resource, null));
	}

}
