package bdp.compalytics.db;

import bdp.compalytics.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionDao {
    Optional<Session> get(String id);
    Optional<Session> getForUser(String userId);
    List<Session> getAll();
    List<Session> getExpired();
    void add(Session session);
    boolean update(Session session);
    boolean delete(String id);
    boolean deleteForUser(String userId);
}
