package bdp.compalytics.db;

import bdp.compalytics.model.NodeRun;

import java.util.List;
import java.util.Optional;

public interface NodeRunDao {
    Optional<NodeRun> get(String runId, String nodeId);
    List<NodeRun> getAll(String runId);
    void save(NodeRun run);
    void delete(String runId, String nodeId);
}
