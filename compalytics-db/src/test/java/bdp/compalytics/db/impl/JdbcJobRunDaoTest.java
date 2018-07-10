package bdp.compalytics.db.impl;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.db.JobRunDao;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobRun;
import bdp.compalytics.model.JobState;
import bdp.compalytics.model.RunState;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class JdbcJobRunDaoTest {
    private static JobDao jobDao;
    private static JobRunDao runDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcJobRunDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");

        DaoFactory daoFactory = new DaoFactory(dataSource);
        jobDao = daoFactory.getJobDao();
        runDao = daoFactory.getJobRunDao();
    }

    @Test
    public void test() {
        Job job = new Job();
        job.setId("job");
        job.setName("name");
        job.setState(JobState.INACTIVE);

        jobDao.add(job);

        JobRun run = new JobRun();
        run.setId("run");
        run.setJobId(job.getId());
        run.setUserId("user");
        run.setAuths(new HashSet<>(asList("A", "B", "C")));
        run.setStart(Instant.now());
        run.setState(RunState.READY);

        runDao.add(run);

        Optional<JobRun> get = runDao.get(job.getId(), run.getId());
        assertTrue(get.isPresent());
        assertEquals(run, get.get());

        List<JobRun> getAll = runDao.getAll(job.getId());
        assertEquals(1, getAll.size());
        assertTrue(getAll.contains(run));

        run.setStop(Instant.now());
        run.setState(RunState.IN_PROGRESS);

        runDao.update(run);

        Optional<JobRun> updated = runDao.get(job.getId(), run.getId());
        assertTrue(updated.isPresent());
        assertEquals(run, updated.get());

        runDao.delete(job.getId(), run.getId());

        assertFalse(runDao.get(job.getId(), run.getId()).isPresent());
        assertTrue(runDao.getAll(job.getId()).isEmpty());
    }
}
