package bdp.compalytics.db.impl;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.EdgeDao;
import bdp.compalytics.model.Edge;
import bdp.compalytics.model.EdgeState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

public class JdbcEdgeDao implements EdgeDao {
    private final DataSource dataSource;

    public JdbcEdgeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, Edge> fromResultSet = (rs) -> {
        try {
            Edge edge = new Edge();
            edge.setId(rs.getString("id"));
            edge.setJobId(rs.getString("job_id"));
            edge.setBeginNode(rs.getString("begin_node"));
            edge.setEndNode(rs.getString("end_node"));
            edge.setLabel(rs.getString("label"));
            edge.setState(EdgeState.valueOf(rs.getString("state")));
            return edge;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve edge from result set", sqlException);
        }
    };

    @Override
    public Optional<Edge> get(String jobId, String id) {
        String sql = "SELECT * FROM edges WHERE job_id = ? AND id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve edge by id", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Edge> getAll(String jobId) {
        String sql = "SELECT * FROM edges WHERE job_id = ?";
        List<Edge> edges = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    edges.add(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve edges for job id", sqlException);
        }
        return edges;
    }

    @Override
    public void save(Edge edge) {
        String sql = "INSERT INTO edges (id, job_id, begin_node, end_node, label, state) "
                + "VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT edges_pk "
                + "DO UPDATE SET label = EXCLUDED.label, state = EXCLUDED.state";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, edge.getId());
            ps.setString(2, edge.getJobId());
            ps.setString(3, edge.getBeginNode());
            ps.setString(4, edge.getEndNode());
            ps.setString(5, edge.getLabel());
            ps.setString(6, edge.getState().name());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to save edge", sqlException);
        }
    }

    @Override
    public void delete(String jobId, String id) {
        String sql = "DELETE FROM edges WHERE job_id = ? AND id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to delete edge", sqlException);
        }
    }
}
