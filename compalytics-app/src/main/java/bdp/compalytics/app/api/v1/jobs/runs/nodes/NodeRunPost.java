package bdp.compalytics.app.api.v1.jobs.runs.nodes;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.NodeRun;
import bdp.compalytics.model.RunState;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Singleton
@Path("/v1/jobs/{jobId}/runs/{runId}/nodes")
@Produces(APPLICATION_JSON)
public class NodeRunPost {
    private final DaoFactory daoFactory;
    private final UriInfo uriInfo;

    @Inject
    public NodeRunPost(DaoFactory daoFactory, UriInfo uriInfo) {
        this.daoFactory = daoFactory;
        this.uriInfo = uriInfo;
    }

    @POST
    public Response saveNodeRun(@PathParam("jobId") String jobId, @PathParam("runId") String runId, NodeRun run) {
        run.setRunId(runId);
        run.setState(ofNullable(run.getState()).orElse(RunState.READY));
        daoFactory.getNodeRunDao().add(run);

        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("api/v1/jobs/{jobId}/runs/{runId}/nodes/{nodeId}")
                .build(jobId, run.getRunId(), run.getNodeId());
        return Response.created(uri).build();
    }
}
