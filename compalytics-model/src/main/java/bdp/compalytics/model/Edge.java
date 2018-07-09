package bdp.compalytics.model;

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
}
