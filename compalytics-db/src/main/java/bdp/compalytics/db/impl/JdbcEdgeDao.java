package bdp.compalytics.db.impl;

import bdp.compalytics.db.EdgeDao;
import bdp.compalytics.model.Edge;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbcEdgeDao implements EdgeDao {
    private final Jdbi jdbi;

    public JdbcEdgeDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<Edge> get(String jobId, String edgeId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM edges WHERE job_id = :jobId AND id = :edgeId")
                .bind("jobId", jobId)
                .bind("edgeId", edgeId)
                .mapToBean(Edge.class)
                .findFirst());
    }

    @Override
    public List<Edge> getAll(String jobId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM edges WHERE job_id = :jobId")
                .bind("jobId", jobId)
                .mapToBean(Edge.class)
                .list());
    }

    @Override
    public void add(Edge edge) {
        String sql = "INSERT INTO edges (id, job_id, begin_node, end_node, label, state) "
                + "VALUES (:id, :jobId, :beginNode, :endNode, :label, :state)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(edge).execute());
    }

    @Override
    public boolean update(Edge edge) {
        String sql = "UPDATE edges SET label = :label, state = :state WHERE job_id = :jobId AND id = :id";
        return jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(edge).execute()) > 0;
    }

    @Override
    public boolean delete(String jobId, String edgeId) {
        return jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM edges WHERE job_id = :jobId AND id = :edgeId")
                .bind("jobId", jobId)
                .bind("edgeId", edgeId)
                .execute()) > 0;
    }
}
