package bdp.compalytics.app.api.v1.jobs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.accepted;
import static javax.ws.rs.core.Response.status;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Error;
import bdp.compalytics.model.Job;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{id}")
@Produces(APPLICATION_JSON)
public class JobPut {
    private final DaoFactory daoFactory;

    @Inject
    public JobPut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response updateJob(@PathParam("id") String id, Job job) {
        job.setId(id);
        boolean updated = daoFactory.getJobDao().update(job);
        if (updated) {
            return accepted(job).build();
        } else {
            Error error = new Error();
            error.setCode(NOT_FOUND.getStatusCode());
            error.setMessage("Job with id " + id + " not found");
            return status(NOT_FOUND).entity(error).build();
        }
    }
}
