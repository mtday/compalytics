package bdp.compalytics.app.api.v1.jobs.nodes;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.accepted;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Node;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/nodes/{nodeId}")
@Produces(APPLICATION_JSON)
public class NodePut {
    private final DaoFactory daoFactory;

    @Inject
    public NodePut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response updateNode(@PathParam("jobId") String jobId, @PathParam("nodeId") String nodeId, Node node) {
        node.setJobId(jobId);
        node.setId(nodeId);
        if (daoFactory.getNodeDao().update(node)) {
            return accepted(node).build();
        }
        throw new NotFoundException("Node with id " + nodeId + " not found for job " + jobId);
    }
}
