package bdp.compalytics.db;

import bdp.compalytics.model.Node;

import java.util.List;
import java.util.Optional;

public interface NodeDao {
    Optional<Node> get(String jobId, String id);
    List<Node> getAll(String jobId);
    void save(Node node);
    void delete(String jobId, String id);
}
