package bdp.compalytics.app.filter;

import static java.util.Optional.ofNullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

@Priority(100)
public class RequestLoggingFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);

    private final UriInfo uriInfo;

    @Inject
    public RequestLoggingFilter(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String user = ofNullable(containerRequestContext.getSecurityContext())
                .map(SecurityContext::getUserPrincipal)
                .map(Principal::getName)
                .orElse("-");
        String method = containerRequestContext.getRequest().getMethod();
        LOGGER.info("{}: {} {}", user, method, uriInfo.getPath());
    }
}
