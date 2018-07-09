package bdp.compalytics.app.api.v1.jobs.runs.nodes;

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
@Path("/api/v1/jobs/{jobId}/runs/{runId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodeRunGet {
    private final DaoFactory daoFactory;

    @Inject
    public NodeRunGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getRun(
            @PathParam("jobId") String jobId,
            @PathParam("runId") String runId,
            @PathParam("nodeId") String nodeId) {
        return daoFactory.getNodeRunDao().get(runId, nodeId)
                .map(node -> ok().entity(node).type(APPLICATION_JSON_TYPE).build())
                .orElseThrow(() -> new NotFoundException(format("Node run with id %s not found", nodeId)));
    }
}
