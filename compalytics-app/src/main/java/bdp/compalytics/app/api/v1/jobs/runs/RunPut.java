package bdp.compalytics.app.api.v1.jobs.runs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.accepted;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.JobRun;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1/jobs/{jobId}/runs/{runId}")
@Produces(APPLICATION_JSON)
public class RunPut {
    private final DaoFactory daoFactory;

    @Inject
    public RunPut(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @PUT
    public Response updateRun(@PathParam("jobId") String jobId, @PathParam("runId") String runId, JobRun run) {
        run.setJobId(jobId);
        run.setId(runId);
        if (daoFactory.getJobRunDao().update(run)) {
            return accepted(run).build();
        }
        throw new NotFoundException("Run with id " + runId + " not found for job " + jobId);
    }
}
