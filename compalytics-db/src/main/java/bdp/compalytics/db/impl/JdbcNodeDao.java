package bdp.compalytics.db.impl;

import bdp.compalytics.db.NodeDao;
import bdp.compalytics.model.Node;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbcNodeDao implements NodeDao {
    private final Jdbi jdbi;

    public JdbcNodeDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<Node> get(String jobId, String id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM nodes WHERE job_id = :jobId AND id = :id")
                .bind("jobId", jobId)
                .bind("id", id)
                .mapToBean(Node.class)
                .findFirst());
    }

    @Override
    public List<Node> getAll(String jobId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM nodes WHERE job_id = :jobId")
                .bind("jobId", jobId)
                .mapToBean(Node.class)
                .list());
    }

    @Override
    public void add(Node node) {
        String sql = "INSERT INTO nodes (id, job_id, name, description, type, class_name, state) "
                + "VALUES (:id, :jobId, :name, :description, :type, :className, :state)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(node).execute());
    }

    @Override
    public void update(Node node) {
        String sql = "UPDATE nodes SET name = :name, description = :description, type = :type, "
                + "class_name = :className, state = :state WHERE job_id = :jobId AND id = :id";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(node).execute());
    }

    @Override
    public void delete(String jobId, String id) {
        jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM nodes WHERE job_id = :jobId AND id = :id")
                .bind("jobId", jobId)
                .bind("id", id)
                .execute());
    }
}
