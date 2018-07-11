package bdp.compalytics.app.api.v1.jobs.runs.nodes;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.GONE;
import static javax.ws.rs.core.Response.status;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/runs/{runId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodeRunDelete {
    private final DaoFactory daoFactory;

    @Inject
    public NodeRunDelete(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @DELETE
    public Response deleteRun(
            @PathParam("jobId") String jobId,
            @PathParam("runId") String runId,
            @PathParam("nodeId") String nodeId) {
        if (daoFactory.getNodeRunDao().delete(jobId, runId, nodeId)) {
            return status(GONE).build();
        }
        throw new NotFoundException("Node run with id " + nodeId + " not found for run " + runId + " and job " + jobId);
    }
}
