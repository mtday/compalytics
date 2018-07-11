package bdp.compalytics.db;

import bdp.compalytics.db.impl.JdbcEdgeDao;
import bdp.compalytics.db.impl.JdbcJobDao;
import bdp.compalytics.db.impl.JdbcJobRunDao;
import bdp.compalytics.db.impl.JdbcNodeDao;
import bdp.compalytics.db.impl.JdbcNodeRunDao;
import bdp.compalytics.db.impl.JdbcSessionDao;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public class DaoFactory {
    private final Jdbi jdbi;

    public DaoFactory(DataSource dataSource) {
        jdbi = Jdbi.create(dataSource);

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    public JobDao getJobDao() {
        return new JdbcJobDao(jdbi);
    }

    public NodeDao getNodeDao() {
        return new JdbcNodeDao(jdbi);
    }

    public EdgeDao getEdgeDao() {
        return new JdbcEdgeDao(jdbi);
    }

    public JobRunDao getJobRunDao() {
        return new JdbcJobRunDao(jdbi);
    }

    public NodeRunDao getNodeRunDao() {
        return new JdbcNodeRunDao(jdbi);
    }

    public SessionDao getSessionDao() {
        return new JdbcSessionDao(jdbi);
    }
}
