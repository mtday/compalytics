package bdp.compalytics.model;

import java.util.Objects;

public class Node {
    private String id;
    private String jobId;
    private String name;
    private String description;
    private NodeType type;
    private String className;
    private NodeState state;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public NodeState getState() {
        return state;
    }

    public void setState(NodeState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        final Node node = (Node) o;
        return Objects.equals(getId(), node.getId()) &&
                Objects.equals(getJobId(), node.getJobId()) &&
                Objects.equals(getName(), node.getName()) &&
                Objects.equals(getDescription(), node.getDescription()) &&
                getType() == node.getType() &&
                Objects.equals(getClassName(), node.getClassName()) &&
                getState() == node.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJobId(), getName(), getDescription(), getType(), getClassName(), getState());
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", jobId='" + jobId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", className='" + className + '\'' +
                ", state=" + state +
                '}';
    }
}
