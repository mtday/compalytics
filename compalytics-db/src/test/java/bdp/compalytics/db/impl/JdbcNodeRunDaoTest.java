package bdp.compalytics.db.impl;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.db.JobRunDao;
import bdp.compalytics.db.NodeDao;
import bdp.compalytics.db.NodeRunDao;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobRun;
import bdp.compalytics.model.JobState;
import bdp.compalytics.model.Node;
import bdp.compalytics.model.NodeRun;
import bdp.compalytics.model.NodeState;
import bdp.compalytics.model.NodeType;
import bdp.compalytics.model.RunState;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class JdbcNodeRunDaoTest {
    private static JobDao jobDao;
    private static NodeDao nodeDao;
    private static JobRunDao jobRunDao;
    private static NodeRunDao nodeRunDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcNodeRunDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");

        DaoFactory daoFactory = new DaoFactory(dataSource);
        jobDao = daoFactory.getJobDao();
        nodeDao = daoFactory.getNodeDao();
        jobRunDao = daoFactory.getJobRunDao();
        nodeRunDao = daoFactory.getNodeRunDao();
    }

    @Test
    public void test() {
        Job job = new Job();
        job.setId("job");
        job.setName("name");
        job.setState(JobState.INACTIVE);

        jobDao.add(job);

        JobRun jobRun = new JobRun();
        jobRun.setId("jobRun");
        jobRun.setJobId(job.getId());
        jobRun.setUserId("user");
        jobRun.setAuths(new HashSet<>(asList("A", "B", "C")));
        jobRun.setStart(Instant.now());
        jobRun.setState(RunState.READY);

        jobRunDao.add(jobRun);

        Node node = new Node();
        node.setId("node");
        node.setJobId(job.getId());
        node.setName("name");
        node.setDescription("desc");
        node.setType(NodeType.SOURCE);
        node.setClassName("class");
        node.setState(NodeState.INACTIVE);

        nodeDao.add(node);

        NodeRun nodeRun = new NodeRun();
        nodeRun.setJobId(job.getId());
        nodeRun.setRunId(jobRun.getId());
        nodeRun.setNodeId(node.getId());
        nodeRun.setStart(Instant.now());
        nodeRun.setState(RunState.READY);

        nodeRunDao.add(nodeRun);

        Optional<NodeRun> get = nodeRunDao.get(job.getId(), jobRun.getId(), node.getId());
        assertTrue(get.isPresent());
        assertEquals(nodeRun, get.get());

        List<NodeRun> getAll = nodeRunDao.getAll(job.getId(), jobRun.getId());
        assertEquals(1, getAll.size());
        assertTrue(getAll.contains(nodeRun));

        nodeRun.setStop(Instant.now());
        nodeRun.setState(RunState.IN_PROGRESS);

        assertTrue(nodeRunDao.update(nodeRun));

        Optional<NodeRun> updated = nodeRunDao.get(job.getId(), jobRun.getId(), nodeRun.getNodeId());
        assertTrue(updated.isPresent());
        assertEquals(nodeRun, updated.get());

        assertTrue(nodeRunDao.delete(job.getId(), jobRun.getId(), nodeRun.getNodeId()));
        assertFalse(nodeRunDao.delete(job.getId(), jobRun.getId(), "missing"));

        assertFalse(nodeRunDao.get(job.getId(), jobRun.getId(), nodeRun.getNodeId()).isPresent());
        assertTrue(nodeRunDao.getAll(job.getId(), jobRun.getId()).isEmpty());
    }
}
