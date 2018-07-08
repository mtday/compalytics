package bdp.compalytics.app.security;

import static bdp.citadel.core.model.AttributeType.AUTH;
import static bdp.citadel.core.model.AttributeType.NAME;

import bdp.citadel.core.model.User;
import bdp.citadel.core.model.UserAttribute;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

public class UserPrincipal  extends User implements Principal {
    private static final long serialVersionUID = 1L;

    public UserPrincipal(User user) {
        super(user.getAttributes());
    }

    @Override
    public String getName() {
        return getAttributes(NAME).stream()
                .map(UserAttribute::getValue)
                .findFirst()
                .orElse("-");
    }

    public String getCombinedAuths() {
        List<String> parts = new LinkedList<>();
        getAttributes(AUTH).stream()
                .map(UserAttribute::getValue)
                .forEach(parts::add);
        getAttributes().stream()
                .map(UserAttribute::toString)
                .forEach(parts::add);
        return String.join(",", parts);
    }
}
