package bdp.compalytics.db;

import bdp.compalytics.model.JobRun;

import java.util.List;
import java.util.Optional;

public interface JobRunDao {
    Optional<JobRun> get(String jobId, String id);
    List<JobRun> getAll(String jobId);
    void add(JobRun run);
    void update(JobRun run);
    void delete(String jobId, String id);
}
