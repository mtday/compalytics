package bdp.compalytics.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.db.NodeDao;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobState;
import bdp.compalytics.model.Node;
import bdp.compalytics.model.NodeState;
import bdp.compalytics.model.NodeType;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class JdbcNodeDaoTest {
    private static JobDao jobDao;
    private static NodeDao nodeDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcNodeDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");

        DaoFactory daoFactory = new DaoFactory(dataSource);
        jobDao = daoFactory.getJobDao();
        nodeDao = daoFactory.getNodeDao();
    }

    @Test
    public void test() {
        Job job = new Job();
        job.setId("job");
        job.setName("name");
        job.setState(JobState.INACTIVE);

        jobDao.add(job);

        Node node = new Node();
        node.setId("node");
        node.setJobId(job.getId());
        node.setName("name");
        node.setDescription("desc");
        node.setType(NodeType.SOURCE);
        node.setClassName("class");
        node.setState(NodeState.INACTIVE);

        nodeDao.add(node);

        Optional<Node> get = nodeDao.get(job.getId(), node.getId());
        assertTrue(get.isPresent());
        assertEquals(node, get.get());

        List<Node> getAll = nodeDao.getAll(job.getId());
        assertEquals(1, getAll.size());
        assertTrue(getAll.contains(node));

        node.setName("updated");
        node.setDescription("updated");
        node.setState(NodeState.RUNNING);

        nodeDao.update(node);

        Optional<Node> updated = nodeDao.get(job.getId(), node.getId());
        assertTrue(updated.isPresent());
        assertEquals(node, updated.get());

        nodeDao.delete(job.getId(), node.getId());

        assertFalse(nodeDao.get(job.getId(), node.getId()).isPresent());
        assertTrue(nodeDao.getAll(job.getId()).isEmpty());
    }
}
