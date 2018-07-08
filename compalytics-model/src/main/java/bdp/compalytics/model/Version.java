package bdp.compalytics.model;

import java.util.Objects;

public class Version {
    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        final Version version1 = (Version) o;
        return Objects.equals(getName(), version1.getName()) &&
                Objects.equals(getVersion(), version1.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVersion());
    }

    @Override
    public String toString() {
        return "Version{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
