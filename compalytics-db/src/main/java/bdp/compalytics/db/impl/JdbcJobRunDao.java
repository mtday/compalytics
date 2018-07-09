package bdp.compalytics.db.impl;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import bdp.compalytics.db.DatabaseException;
import bdp.compalytics.db.JobRunDao;
import bdp.compalytics.model.JobRun;
import bdp.compalytics.model.RunState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.sql.DataSource;

public class JdbcJobRunDao implements JobRunDao {
    private final DataSource dataSource;

    public JdbcJobRunDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Function<ResultSet, JobRun> fromResultSet = (rs) -> {
        try {
            JobRun run = new JobRun();
            run.setId(rs.getString("id"));
            run.setJobId(rs.getString("job_id"));
            run.setUserId(rs.getString("user_id"));
            run.setState(RunState.valueOf(rs.getString("state")));
            run.setAuths(stream(rs.getString("type").split(";")).filter(auth -> !auth.isEmpty()).collect(toSet()));
            run.setStart(rs.getTimestamp("start").toInstant());
            run.setStop(ofNullable(rs.getTimestamp("stop")).map(Timestamp::toInstant).orElse(null));
            return run;
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve run from result set", sqlException);
        }
    };

    @Override
    public Optional<JobRun> get(String jobId, String id) {
        String sql = "SELECT * FROM runs WHERE job_id = ? AND id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve run by id", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public List<JobRun> getAll(String jobId) {
        String sql = "SELECT * FROM runs WHERE job_id = ?";
        List<JobRun> runs = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    runs.add(fromResultSet.apply(rs));
                }
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to retrieve runs for job id", sqlException);
        }
        return runs;
    }

    @Override
    public void save(JobRun run) {
        String sql = "INSERT INTO runs (id, job_id, user_id, state, auths, start, stop) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT runs_pk "
                + "DO UPDATE SET state = EXCLUDED.state, auths = EXCLUDED.auths, start = EXCLUDED.start, "
                + "stop = EXCLUDED.stop";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, run.getId());
            ps.setString(2, run.getJobId());
            ps.setString(3, run.getUserId());
            ps.setString(4, run.getState().name());
            ps.setString(5, String.join(";", run.getAuths()));
            ps.setTimestamp(6, Timestamp.from(run.getStart()));
            ps.setTimestamp(7, ofNullable(run.getStart()).map(Timestamp::from).orElse(null));
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to save run", sqlException);
        }
    }

    @Override
    public void delete(String jobId, String id) {
        String sql = "DELETE FROM runs WHERE job_id = ? AND id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, jobId);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DatabaseException("Failed to delete run", sqlException);
        }
    }
}
