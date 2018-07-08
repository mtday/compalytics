package bdp.compalytics.app.filter;

import static bdp.citadel.core.model.SecurityHeader.BDP_USER;
import static bdp.citadel.core.model.SecurityHeader.BDP_USER_SIGNATURE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import bdp.citadel.core.model.User;
import bdp.citadel.core.model.UserSerializer;
import bdp.citadel.signature.Verifier;
import bdp.citadel.signature.VerifierFactory;
import bdp.citadel.signature.impl.DefaultVerifierFactory;
import bdp.citadel.signature.model.Signature;
import bdp.compalytics.app.security.UserSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStoreException;
import java.security.SignatureException;
import java.util.Properties;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

@Priority(1) // highest priority
public class UserFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilter.class);

    private final VerifierFactory verifierFactory;
    private final UserSerializer userSerializer = new UserSerializer();

    @Inject
    public UserFilter(Properties properties) {
        verifierFactory = ofNullable(properties.getProperty("citadel.signature.cert"))
                .map(cert -> {
                    try {
                        return new DefaultVerifierFactory(cert);
                    } catch (KeyStoreException keyStoreException) {
                        throw new RuntimeException("Failed to load citadel verifier", keyStoreException);
                    }
                })
                .orElse(null);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String serializedUser = containerRequestContext.getHeaderString(BDP_USER.getHeader());
        User user = ofNullable(serializedUser)
                .map(userSerializer::getDeserializedUser)
                .orElseGet(() -> new User(emptyList()));

        if (serializedUser != null && verifierFactory != null) {
            String serializedSignature = containerRequestContext.getHeaderString(BDP_USER_SIGNATURE.getHeader());
            if (serializedSignature == null) {
                LOGGER.error("Signature verification failed due to missing signature");
                user = new User(emptyList());
            } else {
                try {
                    final Signature signature = Signature.deserialize(serializedSignature);
                    final Verifier verifier = this.verifierFactory.getVerifier(signature);
                    final boolean verified = verifier.verify(serializedUser, UTF_8, signature);
                    if (!verified) {
                        LOGGER.error("Signature verification failed due to invalid signature.");
                        user = new User(emptyList());
                    }
                } catch (final IllegalArgumentException | SignatureException invalidSignature) {
                    LOGGER.error("Failed to verify signature", invalidSignature);
                    user = new User(emptyList());
                }
            }
        }

        containerRequestContext.setSecurityContext(new UserSecurityContext(user));
    }
}
