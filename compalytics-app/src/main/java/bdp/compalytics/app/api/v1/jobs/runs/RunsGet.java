package bdp.compalytics.app.api.v1.jobs.runs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/runs")
@Produces(APPLICATION_JSON)
public class RunsGet {
    private final DaoFactory daoFactory;

    @Inject
    public RunsGet(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @GET
    public Response getRunsForJob(@PathParam("jobId") String jobId) {
        return ok().entity(daoFactory.getJobRunDao().getAll(jobId)).type(APPLICATION_JSON_TYPE).build();
    }
}
