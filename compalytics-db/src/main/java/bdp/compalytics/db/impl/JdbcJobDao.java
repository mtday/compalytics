package bdp.compalytics.db.impl;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.model.Job;
import bdp.compalytics.model.JobState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

public class JdbcJobDao implements JobDao {
    private final DataSource dataSource;

    public JdbcJobDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, Job> fromResultSet = (rs) -> {
        try {
            Job job = new Job();
            job.setId(rs.getString("id"));
            job.setName(rs.getString("name"));
            job.setState(JobState.valueOf(rs.getString("state")));
            return job;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve job from result set", sqlException);
        }
    };

    @Override
    public Optional<Job> get(String id) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve job by id", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<Job> getAll() {
        String sql = "SELECT * FROM jobs";
        List<Job> jobs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                jobs.add(fromResultSet.apply(rs));
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve job by id", sqlException);
        }
        return jobs;
    }

    @Override
    public void save(Job job) {
        String sql = "INSERT INTO jobs (id, name, state) VALUES (?, ?, ?) "
                + "ON CONFLICT ON CONSTRAINT jobs_pk "
                + "DO UPDATE SET name = EXCLUDED.name, state = EXCLUDED.state";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, job.getId());
            ps.setString(2, job.getName());
            ps.setString(3, job.getState().name());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to save job", sqlException);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM jobs WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to delete job", sqlException);
        }
    }
}
