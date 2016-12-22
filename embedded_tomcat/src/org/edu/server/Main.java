package org.edu.server;
import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import java.io.File;
import java.net.MalformedURLException;

public class Main {

  public static void main(String[] args) throws Exception, LifecycleException {
    new Main().start();
  }

  public void start() throws ServletException, LifecycleException, MalformedURLException {

    // Define a folder to hold web application contents.
    String webappDirLocation = "WebContent/";
    Tomcat tomcat = new Tomcat();

    // Define port number for the web application
    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty()) {
      webPort = "8080";
    }
    // Bind the port to Tomcat server
    tomcat.setPort(Integer.valueOf(webPort));

    // Define a web application context.
    Context context = tomcat.addWebapp("/tomcatembedded", new File(webappDirLocation).getAbsolutePath());

    // Add servlet that will register Jersey REST resources
    Tomcat.addServlet(context, "jersey-container-servlet", resourceConfig());
    context.addServletMapping("/rest/*", "jersey-container-servlet");

    tomcat.start();
    tomcat.getServer().await();
  }

  private ServletContainer resourceConfig() {
    return new ServletContainer(new ResourceConfig(new ResourceLoader().getClasses()));
  }

}