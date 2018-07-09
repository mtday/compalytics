package bdp.compalytics.db;

import bdp.compalytics.model.Edge;

import java.util.List;
import java.util.Optional;

public interface EdgeDao {
    Optional<Edge> get(String jobId, String id);
    List<Edge> getAll(String jobId);
    void save(Edge edge);
    void delete(String jobId, String id);
}
