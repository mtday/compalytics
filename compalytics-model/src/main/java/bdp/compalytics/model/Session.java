package bdp.compalytics.model;

import java.time.Instant;
import java.util.Objects;

public class Session {
    private String id;
    private String name;
    private String userId;
    private Instant creation;
    private Instant expiration;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getCreation() {
        return creation;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        final Session session = (Session) o;
        return Objects.equals(getId(), session.getId()) &&
                Objects.equals(getName(), session.getName()) &&
                Objects.equals(getUserId(), session.getUserId()) &&
                Objects.equals(getCreation(), session.getCreation()) &&
                Objects.equals(getExpiration(), session.getExpiration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUserId(), getCreation(), getExpiration());
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", creation=" + creation +
                ", expiration=" + expiration +
                '}';
    }
}
