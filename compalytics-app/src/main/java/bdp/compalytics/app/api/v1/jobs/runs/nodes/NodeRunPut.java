package bdp.compalytics.app.api.v1.jobs.runs.nodes;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.accepted;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.NodeRun;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/runs/{runId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodeRunPut {
    private final DaoFactory daoFactory;

    @Inject
    public NodeRunPut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response updateRun(
            @PathParam("jobId") String jobId,
            @PathParam("runId") String runId,
            @PathParam("nodeId") String nodeId,
            NodeRun run) {
        run.setJobId(jobId);
        run.setRunId(runId);
        run.setNodeId(nodeId);
        if (daoFactory.getNodeRunDao().update(run)) {
            return accepted(run).build();
        }
        throw new NotFoundException("Node run with id " + nodeId + " not found for run " + runId + " and job " + jobId);
    }
}
