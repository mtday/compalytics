package bdp.compalytics.app.api.v1.jobs.edges;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/edges/{id}")
@Produces(APPLICATION_JSON)
public class EdgeGet {
    private final DaoFactory daoFactory;

    @Inject
    public EdgeGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getEdge(@PathParam("jobId") String jobId, @PathParam("id") String id) {
        return daoFactory.getEdgeDao().get(jobId, id)
                .map(edge -> ok().entity(edge).type(APPLICATION_JSON_TYPE).build())
                .orElseThrow(() -> new NotFoundException(format("Edge with id %s not found for job %s", id, jobId)));
    }
}
