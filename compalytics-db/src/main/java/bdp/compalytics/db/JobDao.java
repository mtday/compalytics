package bdp.compalytics.db;

import bdp.compalytics.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Optional<Job> get(String jobId);
    List<Job> getAll();
    void add(Job job);
    boolean update(Job job);
    boolean delete(String jobId);
}
