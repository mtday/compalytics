package bdp.compalytics.app.error;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

import bdp.compalytics.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class ErrorExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorExceptionMapper.class);

    private final UriInfo uriInfo;
    private final SecurityContext securityContext;

    @Inject
    public ErrorExceptionMapper(UriInfo uriInfo, SecurityContext securityContext) {
        this.uriInfo = uriInfo;
        this.securityContext = securityContext;
    }

    @Override
    public Response toResponse(Throwable throwable) {
        String user = ofNullable(securityContext.getUserPrincipal()).map(Principal::getName).orElse("-");
        String path = uriInfo.getAbsolutePath().getPath();

        Error error = new Error();
        if (throwable instanceof NotFoundException) {
            LOGGER.error("Not Found: {}", path);
            error.setCode(NOT_FOUND.getStatusCode());
            error.setMessage(throwable.getMessage());
        } else if (throwable instanceof ForbiddenException) {
            LOGGER.error("User {} forbidden access to: {}", user, path);
            error.setCode(FORBIDDEN.getStatusCode());
            error.setMessage(throwable.getMessage());
        } else if (throwable instanceof WebApplicationException) {
            LOGGER.error("User " + user + " exception caught", throwable);
            WebApplicationException wae = (WebApplicationException) throwable;
            error.setCode(wae.getResponse().getStatus());
            error.setMessage(wae.getMessage());
        } else {
            LOGGER.error("User " + user + " exception caught", throwable);
            error.setCode(INTERNAL_SERVER_ERROR.getStatusCode());
            error.setMessage(throwable.getMessage());
        }

        return status(error.getCode()).entity(error).type(APPLICATION_JSON).build();
    }
}
