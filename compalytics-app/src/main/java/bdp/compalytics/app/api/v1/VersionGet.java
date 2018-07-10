package bdp.compalytics.app.api.v1;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.model.Version;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/version")
@Produces(APPLICATION_JSON)
public class VersionGet {
    private final Properties properties;

    @Inject
    public VersionGet(Properties properties) {
        this.properties = properties;
    }

    @GET
    public Response getVersion() {
        Version version = new Version();
        version.setName("Compalytics");
        version.setVersion(properties.getProperty("compalytics.version"));
        return ok().entity(version).type(APPLICATION_JSON_TYPE).build();
    }
}
