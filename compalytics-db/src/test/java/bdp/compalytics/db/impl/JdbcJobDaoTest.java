package bdp.compalytics.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobState;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class JdbcJobDaoTest {
    private static JobDao jobDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcJobDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");

        jobDao = new DaoFactory(dataSource).getJobDao();
    }

    @Test
    public void test() {
        Job job = new Job();
        job.setId("job");
        job.setName("name");
        job.setState(JobState.INACTIVE);

        jobDao.add(job);

        Optional<Job> get = jobDao.get(job.getId());
        assertTrue(get.isPresent());
        assertEquals(job, get.get());

        List<Job> getAll = jobDao.getAll();
        assertEquals(1, getAll.size());
        assertTrue(getAll.contains(job));

        job.setName("updated");
        job.setState(JobState.RUNNING);

        jobDao.update(job);

        Optional<Job> updated = jobDao.get(job.getId());
        assertTrue(updated.isPresent());
        assertEquals(job, updated.get());

        jobDao.delete(job.getId());

        assertFalse(jobDao.get(job.getId()).isPresent());
        assertTrue(jobDao.getAll().isEmpty());
    }
}
