package bdp.compalytics.app.api.v1.jobs.edges;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Edge;
import bdp.compalytics.model.EdgeState;
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
@Path("/api/v1/jobs/{jobId}/edges")
@Produces(APPLICATION_JSON)
public class EdgePost {
    private final DaoFactory daoFactory;
    private final UidSupplier uidSupplier;
    private final UriInfo uriInfo;

    @Inject
    public EdgePost(DaoFactory daoFactory, UidSupplier uidSupplier, UriInfo uriInfo) {
        this.daoFactory = daoFactory;
        this.uidSupplier = uidSupplier;
        this.uriInfo = uriInfo;
    }

    @POST
    public Response saveEdge(@PathParam("jobId") String jobId, Edge edge) {
        edge.setJobId(jobId);
        edge.setId(ofNullable(edge.getId()).orElseGet(uidSupplier));
        edge.setState(ofNullable(edge.getState()).orElse(EdgeState.INACTIVE));
        daoFactory.getEdgeDao().save(edge);

        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("api/v1/jobs/{jobId}/edges/{edgeId}")
                .build(edge.getJobId(), edge.getId());
        return Response.created(uri).build();
    }
}
