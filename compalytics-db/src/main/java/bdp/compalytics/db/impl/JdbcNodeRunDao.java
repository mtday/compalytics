package bdp.compalytics.db.impl;

import bdp.compalytics.db.NodeRunDao;
import bdp.compalytics.model.NodeRun;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbcNodeRunDao implements NodeRunDao {
    private final Jdbi jdbi;

    public JdbcNodeRunDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<NodeRun> get(String jobId, String runId, String nodeId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM node_runs WHERE job_id = :jobId AND run_id = :runId AND node_id = :nodeId")
                .bind("jobId", jobId)
                .bind("runId", runId)
                .bind("nodeId", nodeId)
                .mapToBean(NodeRun.class)
                .findFirst());
    }

    @Override
    public List<NodeRun> getAll(String jobId, String runId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM node_runs WHERE job_id = :jobId AND run_id = :runId")
                .bind("jobId", jobId)
                .bind("runId", runId)
                .mapToBean(NodeRun.class)
                .list());
    }

    @Override
    public void add(NodeRun run) {
        String sql = "INSERT INTO node_runs (job_id, run_id, node_id, state, start, stop) VALUES "
                + "(:jobId, :runId, :nodeId, :state, :start, :stop)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(run).execute());
    }

    @Override
    public boolean update(NodeRun run) {
        String sql = "UPDATE node_runs SET state = :state, stop = :stop "
                + "WHERE job_id = :jobId AND run_id = :runId AND node_id = :nodeId";
        return jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(run).execute()) > 0;
    }

    @Override
    public boolean delete(String jobId, String runId, String nodeId) {
        return jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM node_runs WHERE job_id = :jobId AND run_id = :runId AND node_id = :nodeId")
                .bind("jobId", jobId)
                .bind("runId", runId)
                .bind("nodeId", nodeId)
                .execute()) > 0;
    }
}
