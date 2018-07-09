package bdp.compalytics.db.impl;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.NodeDao;
import bdp.compalytics.model.Node;
import bdp.compalytics.model.NodeState;
import bdp.compalytics.model.NodeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

public class JdbcNodeDao implements NodeDao {
    private final DataSource dataSource;

    public JdbcNodeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, Node> fromResultSet = (rs) -> {
        try {
            Node node = new Node();
            node.setId(rs.getString("id"));
            node.setJobId(rs.getString("job_id"));
            node.setName(rs.getString("name"));
            node.setDescription(rs.getString("description"));
            node.setType(NodeType.valueOf(rs.getString("type")));
            node.setClassName(rs.getString("class_name"));
            node.setState(NodeState.valueOf(rs.getString("state")));
            return node;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve node from result set", sqlException);
        }
    };

    @Override
    public Optional<Node> get(String jobId, String id) {
        String sql = "SELECT * FROM nodes WHERE job_id = ? AND id = ?";
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
            throw new DatabaseException("Failed to retrieve node by id", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Node> getAll(String jobId) {
        String sql = "SELECT * FROM nodes WHERE job_id = ?";
        List<Node> nodes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    nodes.add(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve nodes for job id", sqlException);
        }
        return nodes;
    }

    @Override
    public void save(Node node) {
        String sql = "INSERT INTO nodes (id, job_id, name, description, type, class_name, state) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT nodes_pk "
                + "DO UPDATE SET name = EXCLUDED.name, description = EXCLUDED.description, type = EXCLUDED.type, "
                + "class_name = EXCLUDED.class_name, state = EXCLUDED.state";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, node.getId());
            ps.setString(2, node.getJobId());
            ps.setString(3, node.getName());
            ps.setString(4, node.getDescription());
            ps.setString(5, node.getType().name());
            ps.setString(6, node.getClassName());
            ps.setString(7, node.getState().name());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to save node", sqlException);
        }
    }

    @Override
    public void delete(String jobId, String id) {
        String sql = "DELETE FROM nodes WHERE job_id = ? AND id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to delete node", sqlException);
        }
    }
}
