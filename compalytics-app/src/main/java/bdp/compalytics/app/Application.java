package bdp.compalytics.app;

import bdp.compalytics.app.di.DaoFactoryBinder;
import bdp.compalytics.app.di.DataSourceBinder;
import bdp.compalytics.app.di.PropertiesBinder;
import bdp.compalytics.app.di.RunnerBinder;
import bdp.compalytics.app.di.UidSupplierBinder;
import bdp.compalytics.app.error.ErrorExceptionMapper;
import bdp.compalytics.app.filter.RequestLoggingFilter;
import bdp.compalytics.app.filter.UserFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/*")
public class Application extends ResourceConfig {
    public Application() {
        registerDependencyInjectionObjects();
        registerFilters();

        packages(true, Application.class.getPackage().getName());

        register(ErrorExceptionMapper.class);
    }

    protected void registerFilters() {
        register(UserFilter.class);
        register(RequestLoggingFilter.class);
    }

    protected void registerDependencyInjectionObjects() {
        PropertiesBinder propertiesBinder = new PropertiesBinder();
        register(propertiesBinder);

        DataSourceBinder dataSourceBinder = new DataSourceBinder(propertiesBinder.getProperties());
        register(dataSourceBinder);

        register(new UidSupplierBinder());
        register(new DaoFactoryBinder(dataSourceBinder.getDataSource()));
        register(new RunnerBinder());
    }
}
