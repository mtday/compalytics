package bdp.compalytics.db;

import bdp.compalytics.model.Edge;

import java.util.List;
import java.util.Optional;

public interface EdgeDao {
    Optional<Edge> get(String jobId, String edgeId);
    List<Edge> getAll(String jobId);
    void add(Edge edge);
    boolean update(Edge edge);
    boolean delete(String jobId, String edgeId);
}
