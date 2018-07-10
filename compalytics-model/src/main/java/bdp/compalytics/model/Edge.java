package bdp.compalytics.model;

import java.util.Objects;

public class Edge {
    private String id;
    private String jobId;
    private String beginNode;
    private String endNode;
    private String label;
    private EdgeState state;

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

    public String getBeginNode() {
        return beginNode;
    }

    public void setBeginNode(String beginNode) {
        this.beginNode = beginNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public EdgeState getState() {
        return state;
    }

    public void setState(EdgeState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edge)) {
            return false;
        }
        final Edge edge = (Edge) o;
        return Objects.equals(getId(), edge.getId()) &&
                Objects.equals(getJobId(), edge.getJobId()) &&
                Objects.equals(getBeginNode(), edge.getBeginNode()) &&
                Objects.equals(getEndNode(), edge.getEndNode()) &&
                Objects.equals(getLabel(), edge.getLabel()) &&
                getState() == edge.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJobId(), getBeginNode(), getEndNode(), getLabel(), getState());
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id='" + id + '\'' +
                ", jobId='" + jobId + '\'' +
                ", beginNode='" + beginNode + '\'' +
                ", endNode='" + endNode + '\'' +
                ", label='" + label + '\'' +
                ", state=" + state +
                '}';
    }
}
