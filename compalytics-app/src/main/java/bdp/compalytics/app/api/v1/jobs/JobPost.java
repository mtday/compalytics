package bdp.compalytics.app.api.v1.jobs;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobState;
import bdp.compalytics.service.UidSupplier;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Singleton
@Path("/api/v1/jobs")
@Produces(APPLICATION_JSON)
public class JobPost {
    private final DaoFactory daoFactory;
    private final UidSupplier uidSupplier;
    private final UriInfo uriInfo;

    @Inject
    public JobPost(DaoFactory daoFactory, UidSupplier uidSupplier, UriInfo uriInfo) {
        this.daoFactory = daoFactory;
        this.uidSupplier = uidSupplier;
        this.uriInfo = uriInfo;
    }

    @POST
    public Response saveJob(Job job) {
        job.setId(ofNullable(job.getId()).orElseGet(uidSupplier));
        job.setState(ofNullable(job.getState()).orElse(JobState.INACTIVE));
        daoFactory.getJobDao().save(job);

        URI uri = UriBuilder.fromUri(uriInfo.getBaseUri())
                .path("api/v1/jobs/{jobId}").build(job.getId());
        return Response.created(uri).build();
    }
}
