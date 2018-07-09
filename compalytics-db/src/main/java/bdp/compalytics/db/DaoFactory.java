package bdp.compalytics.db;

import bdp.compalytics.db.impl.JdbcEdgeDao;
import bdp.compalytics.db.impl.JdbcJobDao;
import bdp.compalytics.db.impl.JdbcJobRunDao;
import bdp.compalytics.db.impl.JdbcNodeDao;
import bdp.compalytics.db.impl.JdbcNodeRunDao;
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
        return new JdbcJobDao(dataSource);
    }

    public NodeDao getNodeDao() {
        return new JdbcNodeDao(dataSource);
    }

    public EdgeDao getEdgeDao() {
        return new JdbcEdgeDao(dataSource);
    }

    public JobRunDao getJobRunDao() {
        return new JdbcJobRunDao(dataSource);
    }

    public NodeRunDao getNodeRunDao() {
        return new JdbcNodeRunDao(dataSource);
    }
}
