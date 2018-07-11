package bdp.compalytics.db;

import bdp.compalytics.model.Node;

import java.util.List;
import java.util.Optional;

public interface NodeDao {
    Optional<Node> get(String jobId, String nodeId);
    List<Node> getAll(String jobId);
    void add(Node node);
    boolean update(Node node);
    boolean delete(String jobId, String nodeId);
}
