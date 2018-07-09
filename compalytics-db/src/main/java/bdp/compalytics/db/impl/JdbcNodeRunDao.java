package bdp.compalytics.db.impl;

import static java.util.Optional.ofNullable;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.NodeRunDao;
import bdp.compalytics.model.NodeRun;
import bdp.compalytics.model.RunState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

public class JdbcNodeRunDao implements NodeRunDao {
    private final DataSource dataSource;

    public JdbcNodeRunDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, NodeRun> fromResultSet = (rs) -> {
        try {
            NodeRun run = new NodeRun();
            run.setRunId(rs.getString("run_id"));
            run.setNodeId(rs.getString("node_id"));
            run.setState(RunState.valueOf(rs.getString("state")));
            run.setStart(rs.getTimestamp("start").toInstant());
            run.setStop(ofNullable(rs.getTimestamp("stop")).map(Timestamp::toInstant).orElse(null));
            return run;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve run from result set", sqlException);
        }
    };

    @Override
    public Optional<NodeRun> get(String runId, String nodeId) {
        String sql = "SELECT * FROM node_runs WHERE run_id = ? AND node_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, runId);
            ps.setString(2, nodeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve run by id", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<NodeRun> getAll(String runId) {
        String sql = "SELECT * FROM node_runs WHERE run_id = ?";
        List<NodeRun> runs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, runId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    runs.add(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve node runs for run id", sqlException);
        }
        return runs;
    }

    @Override
    public void save(NodeRun run) {
        String sql = "INSERT INTO node_runs (run_id, node_id, state, start, stop) VALUES (?, ?, ?, ?, ?) "
                + "ON CONFLICT ON CONSTRAINT node_runs_pk "
                + "DO UPDATE SET state = EXCLUDED.state, start = EXCLUDED.start, stop = EXCLUDED.stop";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, run.getRunId());
            ps.setString(2, run.getNodeId());
            ps.setString(3, run.getState().name());
            ps.setTimestamp(4, Timestamp.from(run.getStart()));
            ps.setTimestamp(5, ofNullable(run.getStop()).map(Timestamp::from).orElse(null));
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to save node run", sqlException);
        }
    }

    @Override
    public void delete(String runId, String nodeId) {
        String sql = "DELETE FROM node_runs WHERE run_id = ? AND node_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, runId);
            ps.setString(2, nodeId);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to delete node run", sqlException);
        }
    }
}
