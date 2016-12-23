package org.edu;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

/**
 * This is not mocking actually. Here not using JUnit or neither of the WireMock
 * rules manage its life cycle in a suitable way you can construct and start the
 * server directly.
 * 
 * @author shivam.maharshi
 */
public class WebServer {

	WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8080));

	// TODO: Finish this to build a server full configurations.
	public void execute() {
		wireMockServer.start();
		wireMockServer.isRunning();
		wireMockServer.listAllStubMappings();
		wireMockServer.stop();
	}
}
