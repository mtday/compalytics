package bdp.compalytics.model;

import java.util.Objects;

public class Job {
    private String id;
    private String name;
    private JobState state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        final Job job = (Job) o;
        return Objects.equals(getId(), job.getId()) &&
                Objects.equals(getName(), job.getName()) &&
                getState() == job.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getState());
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
