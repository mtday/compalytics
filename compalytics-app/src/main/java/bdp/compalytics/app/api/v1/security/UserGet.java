package bdp.compalytics.app.api.v1.security;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.citadel.core.model.User;
import bdp.compalytics.app.security.UserPrincipal;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Singleton
@Path("/v1/security/user")
@Produces(APPLICATION_JSON)
public class UserGet {
    private final SecurityContext securityContext;

    @Inject
    public UserGet(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @GET
    public Response whoami() {
        User user = ((UserPrincipal) securityContext.getUserPrincipal()).getUser();
        return ok().entity(user).type(APPLICATION_JSON_TYPE).build();
    }
}
