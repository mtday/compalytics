package bdp.compalytics.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class JobRun {
    private String id;
    private String jobId;
    private String userId;
    private RunState state;
    private Set<String> auths;
    private Instant start;
    private Instant stop;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public RunState getState() {
        return state;
    }

    public void setState(RunState state) {
        this.state = state;
    }

    public Set<String> getAuths() {
        return auths;
    }

    public void setAuths(Set<String> auths) {
        this.auths = auths;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getStop() {
        return stop;
    }

    public void setStop(Instant stop) {
        this.stop = stop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobRun)) {
            return false;
        }
        final JobRun jobRun = (JobRun) o;
        return Objects.equals(getId(), jobRun.getId()) &&
                Objects.equals(getJobId(), jobRun.getJobId()) &&
                Objects.equals(getUserId(), jobRun.getUserId()) &&
                getState() == jobRun.getState() &&
                Objects.equals(getAuths(), jobRun.getAuths()) &&
                Objects.equals(getStart(), jobRun.getStart()) &&
                Objects.equals(getStop(), jobRun.getStop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJobId(), getUserId(), getState(), getAuths(), getStart(), getStop());
    }

    @Override
    public String toString() {
        return "JobRun{" +
                "id='" + id + '\'' +
                ", jobId='" + jobId + '\'' +
                ", userId='" + userId + '\'' +
                ", state=" + state +
                ", auths=" + auths +
                ", start=" + start +
                ", stop=" + stop +
                '}';
    }
}
