package bdp.compalytics.app.api.v1.jobs.runs.nodes;

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
@Path("/api/v1/jobs/{jobId}/runs/{runId}/nodes")
@Produces(APPLICATION_JSON)
public class NodeRunsGet {
    private final DaoFactory daoFactory;

    @Inject
    public NodeRunsGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getNodeRuns(@PathParam("jobId") String jobId, @PathParam("runId") String runId) {
        return ok().entity(daoFactory.getNodeRunDao().getAll(runId)).type(APPLICATION_JSON_TYPE).build();
    }
}
