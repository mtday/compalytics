package bdp.compalytics.app.di;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesBinder extends AbstractBinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesBinder.class);
    private static final String PROPERTIES = "compalytics.properties";

    private final Properties properties;

    public PropertiesBinder() {
        properties = new Properties();
        try (InputStream inputStream = PropertiesBinder.class.getClassLoader().getResourceAsStream(PROPERTIES)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.warn("Failed to find system properties resource ({}), will use default values");
            }
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to load system properties", ioException);
        }
    }

    public Properties getProperties() {
        return properties;
    }

    protected void configure() {
        bind(properties).to(Properties.class);
    }
}
