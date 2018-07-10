package bdp.compalytics.app.api.v1.jobs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
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
    public Response saveJob(@PathParam("id") String id, Job job) {
        job.setId(id);
        daoFactory.getJobDao().update(job);
        return Response.accepted(job).build();
    }
}
