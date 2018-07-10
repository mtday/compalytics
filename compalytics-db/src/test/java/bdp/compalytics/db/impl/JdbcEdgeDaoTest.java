package bdp.compalytics.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.EdgeDao;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.db.NodeDao;
import bdp.compalytics.model.Edge;
import bdp.compalytics.model.EdgeState;
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

public class JdbcEdgeDaoTest {
    private static JobDao jobDao;
    private static NodeDao nodeDao;
    private static EdgeDao edgeDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcEdgeDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");

        DaoFactory daoFactory = new DaoFactory(dataSource);
        jobDao = daoFactory.getJobDao();
        nodeDao = daoFactory.getNodeDao();
        edgeDao = daoFactory.getEdgeDao();
    }

    @Test
    public void test() {
        Job job = new Job();
        job.setId("job");
        job.setName("name");
        job.setState(JobState.INACTIVE);

        jobDao.add(job);

        Node node1 = new Node();
        node1.setId("node1");
        node1.setJobId(job.getId());
        node1.setName("name");
        node1.setDescription("desc");
        node1.setType(NodeType.SOURCE);
        node1.setClassName("class");
        node1.setState(NodeState.INACTIVE);

        Node node2 = new Node();
        node2.setId("node2");
        node2.setJobId(job.getId());
        node2.setName("name");
        node2.setDescription("desc");
        node2.setType(NodeType.SOURCE);
        node2.setClassName("class");
        node2.setState(NodeState.INACTIVE);

        nodeDao.add(node1);
        nodeDao.add(node2);

        Edge edge = new Edge();
        edge.setId("edge");
        edge.setJobId(job.getId());
        edge.setBeginNode(node1.getId());
        edge.setEndNode(node2.getId());
        edge.setLabel("label");
        edge.setState(EdgeState.INACTIVE);

        edgeDao.add(edge);

        Optional<Edge> get = edgeDao.get(job.getId(), edge.getId());
        assertTrue(get.isPresent());
        assertEquals(edge, get.get());

        List<Edge> getAll = edgeDao.getAll(job.getId());
        assertEquals(1, getAll.size());
        assertTrue(getAll.contains(edge));

        edge.setLabel("updated");
        edge.setState(EdgeState.ACTIVE);

        edgeDao.update(edge);

        Optional<Edge> updated = edgeDao.get(job.getId(), edge.getId());
        assertTrue(updated.isPresent());
        assertEquals(edge, updated.get());

        edgeDao.delete(job.getId(), edge.getId());

        assertFalse(edgeDao.get(job.getId(), edge.getId()).isPresent());
        assertTrue(edgeDao.getAll(job.getId()).isEmpty());
    }
}
