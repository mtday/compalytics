package bdp.compalytics.db;

import bdp.compalytics.model.NodeRun;

import java.util.List;
import java.util.Optional;

public interface NodeRunDao {
    Optional<NodeRun> get(String jobId, String runId, String nodeId);
    List<NodeRun> getAll(String jobId, String runId);
    void add(NodeRun run);
    boolean update(NodeRun run);
    boolean delete(String jobId, String runId, String nodeId);
}
