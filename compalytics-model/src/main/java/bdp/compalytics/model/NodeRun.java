package bdp.compalytics.model;

import java.time.Instant;
import java.util.Objects;

public class NodeRun {
    private String jobId;
    private String runId;
    private String nodeId;
    private RunState state;
    private Instant start;
    private Instant stop;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public RunState getState() {
        return state;
    }

    public void setState(RunState state) {
        this.state = state;
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
        if (!(o instanceof NodeRun)) {
            return false;
        }
        final NodeRun nodeRun = (NodeRun) o;
        return Objects.equals(getJobId(), nodeRun.getJobId()) &&
                Objects.equals(getRunId(), nodeRun.getRunId()) &&
                Objects.equals(getNodeId(), nodeRun.getNodeId()) &&
                getState() == nodeRun.getState() &&
                Objects.equals(getStart(), nodeRun.getStart()) &&
                Objects.equals(getStop(), nodeRun.getStop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJobId(), getRunId(), getNodeId(), getState(), getStart(), getStop());
    }

    @Override
    public String toString() {
        return "NodeRun{" +
                "jobId='" + jobId + '\'' +
                ", runId='" + runId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", state=" + state +
                ", start=" + start +
                ", stop=" + stop +
                '}';
    }
}
