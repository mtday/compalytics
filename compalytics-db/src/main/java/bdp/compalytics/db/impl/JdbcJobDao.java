package bdp.compalytics.db.impl;

import bdp.compalytics.db.JobDao;
import bdp.compalytics.model.Job;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

public class JdbcJobDao implements JobDao {
    private final Jdbi jdbi;

    public JdbcJobDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<Job> get(String jobId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM jobs WHERE id = :jobId")
                .bind("jobId", jobId)
                .mapToBean(Job.class)
                .findFirst());
    }

    @Override
    public List<Job> getAll() {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM jobs")
                .mapToBean(Job.class)
                .list());
    }

    @Override
    public void add(Job job) {
        String sql = "INSERT INTO jobs (id, name, state) VALUES (:id, :name, :state)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(job).execute());
    }

    @Override
    public boolean update(Job job) {
        String sql = "UPDATE jobs SET name = :name, state = :state WHERE id = :id";
        return jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(job).execute()) > 0;
    }

    @Override
    public boolean delete(String jobId) {
        return jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM jobs WHERE id = :jobId")
                .bind("jobId", jobId)
                .execute()) > 0;
    }
}
