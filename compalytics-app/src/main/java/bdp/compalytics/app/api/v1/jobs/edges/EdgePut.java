package bdp.compalytics.app.api.v1.jobs.edges;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Edge;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/edges/{id}")
@Produces(APPLICATION_JSON)
public class EdgePut {
    private final DaoFactory daoFactory;

    @Inject
    public EdgePut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response saveEdge(@PathParam("jobId") String jobId, @PathParam("id") String id, Edge edge) {
        edge.setJobId(jobId);
        edge.setId(id);
        daoFactory.getEdgeDao().update(edge);
        return Response.accepted(edge).build();
    }
}
