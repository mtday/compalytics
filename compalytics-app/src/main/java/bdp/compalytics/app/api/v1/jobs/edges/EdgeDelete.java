package bdp.compalytics.app.api.v1.jobs.edges;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.GONE;
import static javax.ws.rs.core.Response.status;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Edge;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/edges/{edgeId}")
@Produces(APPLICATION_JSON)
public class EdgeDelete {
    private final DaoFactory daoFactory;

    @Inject
    public EdgeDelete(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @DELETE
    public Response deleteEdge(@PathParam("jobId") String jobId, @PathParam("edgeId") String edgeId, Edge edge) {
        edge.setJobId(jobId);
        edge.setId(edgeId);
        if (daoFactory.getEdgeDao().update(edge)) {
            return status(GONE).build();
        }
        throw new NotFoundException("Edge with id " + edgeId + " not found for job " + jobId);
    }
}
