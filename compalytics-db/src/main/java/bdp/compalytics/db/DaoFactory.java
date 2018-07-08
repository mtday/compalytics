package bdp.compalytics.db;

import bdp.compalytics.db.impl.PostgresJobDao;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class DaoFactory {
    private final DataSource dataSource;

    public DaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    public JobDao getJobDao() {
        return new PostgresJobDao(dataSource);
    }
}
