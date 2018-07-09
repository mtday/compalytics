package bdp.compalytics.app.api.v1.jobs.runs.nodes;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.NodeRun;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/api/v1/jobs/{jobId}/runs/{runId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodeRunPut {
    private final DaoFactory daoFactory;

    @Inject
    public NodeRunPut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response saveRun(@PathParam("jobId") String jobId, @PathParam("runId") String runId, NodeRun run) {
        run.setRunId(runId);
        daoFactory.getNodeRunDao().save(run);
        return Response.accepted(run).build();
    }
}
