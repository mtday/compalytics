package bdp.compalytics.db.impl;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import bdp.compalytics.db.JobRunDao;
import bdp.compalytics.model.JobRun;
import bdp.compalytics.model.RunState;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class JdbcJobRunDao implements JobRunDao {
    private final Jdbi jdbi;

    public JdbcJobRunDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    private class JobRunRowMapper implements RowMapper<JobRun> {
        @Override
        public JobRun map(ResultSet rs, StatementContext ctx) throws SQLException {
            JobRun run = new JobRun();
            run.setId(rs.getString("id"));
            run.setJobId(rs.getString("job_id"));
            run.setUserId(rs.getString("user_id"));
            run.setAuths(stream(ofNullable(rs.getString("auths")).orElse("").split(";"))
                    .filter(auth -> !auth.isEmpty()).collect(toSet()));
            run.setState(RunState.valueOf(rs.getString("state")));
            run.setStart(rs.getTimestamp("start").toInstant());
            run.setStop(ofNullable(rs.getTimestamp("stop")).map(Timestamp::toInstant).orElse(null));
            return run;
        }
    }

    @Override
    public Optional<JobRun> get(String jobId, String id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM runs WHERE job_id = :jobId AND id = :id")
                .bind("jobId", jobId)
                .bind("id", id)
                .map(new JobRunRowMapper())
                .findFirst());
    }

    @Override
    public List<JobRun> getAll(String jobId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM runs WHERE job_id = :jobId ORDER BY start DESC")
                .bind("jobId", jobId)
                .map(new JobRunRowMapper())
                .list());
    }

    @Override
    public void add(JobRun run) {
        String sql = "INSERT INTO runs (id, job_id, user_id, state, auths, start, stop) "
                + "VALUES (:id, :jobId, :userId, :state, :auths, :start, :stop)";
        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("id", run.getId())
                .bind("jobId", run.getJobId())
                .bind("userId", run.getUserId())
                .bind("state", run.getState())
                .bind("auths", String.join(";", run.getAuths()))
                .bind("start", run.getStart())
                .bind("stop", run.getStop())
                .execute());
    }

    @Override
    public void update(JobRun run) {
        String sql = "UPDATE runs SET state = :state, stop = :stop WHERE job_id = :jobId AND id = :id";
        jdbi.withHandle(handle -> handle.createUpdate(sql)
                .bind("state", run.getState())
                .bind("stop", run.getStop())
                .bind("jobId", run.getJobId())
                .bind("id", run.getId())
                .execute());
    }

    @Override
    public void delete(String jobId, String id) {
        jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM runs WHERE job_id = :jobId AND id = :id")
                .bind("jobId", jobId)
                .bind("id", id)
                .execute());
    }
}
