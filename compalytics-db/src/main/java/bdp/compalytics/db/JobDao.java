package bdp.compalytics.db;

import bdp.compalytics.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Optional<Job> get(String id);
    List<Job> getAll();
    void add(Job job);
    void update(Job job);
    void delete(String id);
}
