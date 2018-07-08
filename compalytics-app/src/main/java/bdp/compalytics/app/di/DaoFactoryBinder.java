package bdp.compalytics.app.di;

import bdp.compalytics.db.DaoFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.sql.DataSource;

public class DaoFactoryBinder extends AbstractBinder {
    private final DaoFactory daoFactory;

    public DaoFactoryBinder(DataSource dataSource) {
        daoFactory = new DaoFactory(dataSource);
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    protected void configure() {
        bind(getDaoFactory()).to(DaoFactory.class);
    }
}
