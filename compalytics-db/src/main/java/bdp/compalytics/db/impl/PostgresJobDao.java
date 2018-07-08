package bdp.compalytics.db.impl;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.JobDao;
import bdp.compalytics.model.Job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

public class PostgresJobDao implements JobDao {
    private final DataSource dataSource;

    public PostgresJobDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Job> get(String id) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Job job = new Job();
                    job.setId(id);
                    job.setName(rs.getString("name"));
                    return Optional.of(job);
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
                Job job = new Job();
                job.setId(rs.getString("id"));
                job.setName(rs.getString("name"));
                jobs.add(job);
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve job by id", sqlException);
        }
        return jobs;
    }
}
