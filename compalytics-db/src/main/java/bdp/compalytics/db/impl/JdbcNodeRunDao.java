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
    public Optional<NodeRun> get(String runId, String nodeId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM node_runs WHERE run_id = :runId AND node_id = :nodeId")
                .bind("runId", runId)
                .bind("nodeId", nodeId)
                .mapToBean(NodeRun.class)
                .findFirst());
    }

    @Override
    public List<NodeRun> getAll(String runId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM node_runs WHERE run_id = :runId")
                .bind("runId", runId)
                .mapToBean(NodeRun.class)
                .list());
    }

    @Override
    public void add(NodeRun run) {
        String sql = "INSERT INTO node_runs (run_id, node_id, state, start, stop) VALUES "
                + "(:runId, :nodeId, :state, :start, :stop)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(run).execute());
    }

    @Override
    public void update(NodeRun run) {
        String sql = "UPDATE node_runs SET state = :state, stop = :stop WHERE run_id = :runId AND node_id = :nodeId";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(run).execute());
    }

    @Override
    public void delete(String runId, String nodeId) {
        jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM node_runs WHERE run_id = :runId AND node_id = :nodeId")
                .bind("runId", runId)
                .bind("nodeId", nodeId)
                .execute());
    }
}
