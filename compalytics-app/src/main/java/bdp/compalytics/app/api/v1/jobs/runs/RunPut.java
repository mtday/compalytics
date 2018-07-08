package bdp.compalytics.app.api.v1.jobs.runs;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.ok;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.model.Job;
import bdp.compalytics.service.UidSupplier;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Singleton
@Path("/api/v1/jobs/{id}/runs")
@Produces(APPLICATION_JSON)
public class RunPut {
    private final DaoFactory daoFactory;
    private final UidSupplier uidSupplier;

    @Inject
    public RunPut(DaoFactory daoFactory, UidSupplier uidSupplier) {
        this.daoFactory = daoFactory;
        this.uidSupplier = uidSupplier;
    }

    @PUT
    public Response requestRun(@PathParam("id") String id) {
        Optional<Job> job = daoFactory.getJobDao().get(id);
        // TODO
        String runId = uidSupplier.get();
        return ok().entity(runId).type(APPLICATION_JSON_TYPE).build();
    }
}
