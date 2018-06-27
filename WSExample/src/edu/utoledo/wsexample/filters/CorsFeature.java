package edu.utoledo.wsexample.filters;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

/**
 * Adds the Cors Registration header to all outgoing responses
 * @author Anil Sehgal
 */
@Provider
public class CorsFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
    	
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        context.register(corsFilter);
        return true;
    }  
}
