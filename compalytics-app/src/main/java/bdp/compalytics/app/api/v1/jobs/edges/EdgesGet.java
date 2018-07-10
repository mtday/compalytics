package bdp.compalytics.app.api.v1.jobs.edges;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/edges")
@Produces(APPLICATION_JSON)
public class EdgesGet {
    private final DaoFactory daoFactory;

    @Inject
    public EdgesGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getEdgesForJob(@PathParam("jobId") String jobId) {
        return ok().entity(daoFactory.getNodeDao().getAll(jobId)).type(APPLICATION_JSON_TYPE).build();
    }
}
