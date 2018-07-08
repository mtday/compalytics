package bdp.compalytics.app.api.v1.jobs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/api/v1/jobs")
@Produces(APPLICATION_JSON)
public class JobsGet {
    private final DaoFactory daoFactory;

    @Inject
    public JobsGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getAllJobs() {
        return ok().entity(daoFactory.getJobDao().getAll()).type(APPLICATION_JSON_TYPE).build();
    }
}
