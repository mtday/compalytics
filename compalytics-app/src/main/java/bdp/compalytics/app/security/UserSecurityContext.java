package bdp.compalytics.app.security;

import static bdp.citadel.core.model.AttributeType.ROLE;

import bdp.citadel.core.model.User;
import bdp.citadel.core.model.UserAttribute;
import bdp.citadel.core.model.UserSerializer;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class UserSecurityContext implements SecurityContext {
    private final UserPrincipal user;
    private final UserSerializer userSerializer = new UserSerializer();

    public UserSecurityContext(User user) {
        this.user = new UserPrincipal(user);
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        boolean allowed = user.hasAttribute(new UserAttribute(ROLE, role));
        if (role.contains(":")) {
            allowed |= user.hasAttribute(userSerializer.getDeserializedAttribute(role));
        }
        return allowed;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "citadel";
    }
}
