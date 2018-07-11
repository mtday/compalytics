package bdp.compalytics.app.api.v1.jobs.nodes;

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
@Path("/v1/jobs/{jobId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodeDelete {
    private final DaoFactory daoFactory;

    @Inject
    public NodeDelete(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @DELETE
    public Response deleteNode(@PathParam("jobId") String jobId, @PathParam("nodeId") String nodeId) {
        if (daoFactory.getNodeDao().delete(jobId, nodeId)) {
            return status(GONE).build();
        }
        throw new NotFoundException("Node with id " + nodeId + " not found for job " + jobId);
    }
}
