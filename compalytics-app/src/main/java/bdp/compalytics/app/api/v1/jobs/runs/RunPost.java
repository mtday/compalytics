package bdp.compalytics.app.api.v1.jobs.runs;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.JobRun;
import bdp.compalytics.model.RunState;
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
@Path("/v1/jobs/{jobId}/runs")
@Produces(APPLICATION_JSON)
public class RunPost {
    private final DaoFactory daoFactory;
    private final UidSupplier uidSupplier;
    private final UriInfo uriInfo;

    @Inject
    public RunPost(DaoFactory daoFactory, UidSupplier uidSupplier, UriInfo uriInfo) {
        this.daoFactory = daoFactory;
        this.uidSupplier = uidSupplier;
        this.uriInfo = uriInfo;
    }

    @POST
    public Response saveRun(@PathParam("jobId") String jobId, JobRun run) {
        run.setJobId(jobId);
        run.setId(ofNullable(run.getId()).orElseGet(uidSupplier));
        run.setState(ofNullable(run.getState()).orElse(RunState.READY));
        daoFactory.getJobRunDao().add(run);

        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("api/v1/jobs/{jobId}/runs/{runId}")
                .build(run.getJobId(), run.getId());
        return Response.created(uri).build();
    }
}
