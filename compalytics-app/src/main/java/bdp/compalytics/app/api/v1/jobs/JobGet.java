package bdp.compalytics.app.api.v1.jobs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{id}")
@Produces(APPLICATION_JSON)
public class JobGet {
    private final DaoFactory daoFactory;

    @Inject
    public JobGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getJob(@PathParam("id") String id) {
        return daoFactory.getJobDao().get(id)
                .map(job -> ok().entity(job).type(APPLICATION_JSON_TYPE).build())
                .orElseThrow(() -> new NotFoundException("Job with id " + id + " not found"));
    }
}
