package org.edu.server;
import java.util.HashSet;
import java.util.Set;
 
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

public class ResourceLoader extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<Class<?>>();
    // register root resource
    classes.add(HelloWorldRestResource.class);
    return classes;
  }
}