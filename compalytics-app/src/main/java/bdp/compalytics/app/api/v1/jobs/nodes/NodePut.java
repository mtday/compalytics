package bdp.compalytics.app.api.v1.jobs.nodes;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Node;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/api/v1/jobs/{jobId}/nodes/{id}")
@Produces(APPLICATION_JSON)
public class NodePut {
    private final DaoFactory daoFactory;

    @Inject
    public NodePut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response saveNode(@PathParam("jobId") String jobId, @PathParam("id") String id, Node node) {
        node.setJobId(jobId);
        node.setId(id);
        daoFactory.getNodeDao().save(node);
        return Response.accepted(node).build();
    }
}
