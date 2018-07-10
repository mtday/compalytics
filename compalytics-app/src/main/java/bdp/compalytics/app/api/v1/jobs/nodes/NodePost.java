package bdp.compalytics.app.api.v1.jobs.nodes;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Node;
import bdp.compalytics.model.NodeState;
import bdp.compalytics.service.UidSupplier;

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
@Path("/v1/jobs/{jobId}/nodes")
@Produces(APPLICATION_JSON)
public class NodePost {
    private final DaoFactory daoFactory;
    private final UidSupplier uidSupplier;
    private final UriInfo uriInfo;

    @Inject
    public NodePost(DaoFactory daoFactory, UidSupplier uidSupplier, UriInfo uriInfo) {
        this.daoFactory = daoFactory;
        this.uidSupplier = uidSupplier;
        this.uriInfo = uriInfo;
    }

    @POST
    public Response saveNode(@PathParam("jobId") String jobId, Node node) {
        node.setJobId(jobId);
        node.setId(ofNullable(node.getId()).orElseGet(uidSupplier));
        node.setState(ofNullable(node.getState()).orElse(NodeState.INACTIVE));
        daoFactory.getNodeDao().add(node);

        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("api/v1/jobs/{jobId}/nodes/{edgeId}")
                .build(node.getJobId(), node.getId());
        return Response.created(uri).build();
    }
}
