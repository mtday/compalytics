package bdp.compalytics.db;

import bdp.compalytics.model.JobRun;

import java.util.List;
import java.util.Optional;

public interface JobRunDao {
    Optional<JobRun> get(String jobId, String runId);
    List<JobRun> getAll(String jobId);
    void add(JobRun run);
    boolean update(JobRun run);
    boolean delete(String jobId, String runId);
}
