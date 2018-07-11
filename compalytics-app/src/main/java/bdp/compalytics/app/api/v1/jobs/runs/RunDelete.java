package bdp.compalytics.app.api.v1.jobs.runs;

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
@Path("/v1/jobs/{jobId}/runs/{id}")
@Produces(APPLICATION_JSON)
public class RunDelete {
    private final DaoFactory daoFactory;

    @Inject
    public RunDelete(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @DELETE
    public Response deleteRun(@PathParam("jobId") String jobId, @PathParam("id") String id) {
        if (daoFactory.getJobRunDao().delete(jobId, id)) {
            return status(GONE).build();
        }
        throw new NotFoundException("Run with id " + id + " not found for job " + jobId);
    }
}
