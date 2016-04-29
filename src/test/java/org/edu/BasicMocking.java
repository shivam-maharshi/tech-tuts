package org.edu;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.notMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import java.util.HashMap;
import static org.junit.Assert.*;

import org.edu.RestClient.Status;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

/**
 * Mocking Rest services with the easiest and the most straight forward usage
 * using stubbing.
 * 
 * @author shivam.maharshi
 */
public class BasicMocking {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8080));

	public RestClient rc = new RestClient(8080, "myService/");

	@Test
	public void exampleTest() {
		stubFor(get(urlEqualTo("/myService/1")).withHeader("Accept", equalTo("*/*")).willReturn(aResponse()
				.withStatus(200).withHeader("Content-Type", "text/xml").withBody("<response>Some content</response>")));

		HashMap<String, String> result = new HashMap<String, String>();
		Status status = rc.read(null, "1", null, result);
		assertEquals(status, Status.OK);

		// verify(postRequestedFor(urlMatching("/my/resource/[a-z0-9]+"))
		// .withRequestBody(matching(".*<message>1234</message>.*"))
		// .withHeader("Content-Type", notMatching("application/json")));
	}

}
