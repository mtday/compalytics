package bdp.compalytics.db.impl;

import bdp.compalytics.db.SessionDao;
import bdp.compalytics.model.Session;
import org.jdbi.v3.core.Jdbi;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class JdbcSessionDao implements SessionDao {
    private final Jdbi jdbi;

    public JdbcSessionDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<Session> get(String id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM sessions WHERE id = :id")
                .bind("id", id)
                .mapToBean(Session.class)
                .findFirst());
    }

    @Override
    public Optional<Session> getForUser(String userId) {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM sessions WHERE user_id = :userId")
                .bind("userId", userId)
                .mapToBean(Session.class)
                .findFirst());
    }

    @Override
    public List<Session> getAll() {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM sessions")
                .mapToBean(Session.class)
                .list());
    }

    @Override
    public List<Session> getExpired() {
        return jdbi.withHandle(handle -> handle
                .createQuery("SELECT * FROM sessions WHERE expiration < :expiration")
                .bind("expiration", Timestamp.from(Instant.now()))
                .mapToBean(Session.class)
                .list());
    }

    @Override
    public void add(Session session) {
        String sql = "INSERT INTO sessions (id, name, user_id, creation, expiration) VALUES "
                + "(:id, :name, :userId, :creation, :expiration)";
        jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(session).execute());
    }

    @Override
    public boolean update(Session session) {
        String sql = "UPDATE sessions SET name = :name, expiration = :expiration WHERE id = :id AND user_id = :userId";
        return jdbi.withHandle(handle -> handle.createUpdate(sql).bindBean(session).execute()) > 0;
    }

    @Override
    public boolean delete(String id) {
        return jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM sessions WHERE id = :id")
                .bind("id", id)
                .execute()) > 0;
    }

    @Override
    public boolean deleteForUser(String userId) {
        return jdbi.withHandle(handle -> handle
                .createUpdate("DELETE FROM sessions WHERE user_id = :userId")
                .bind("userId", userId)
                .execute()) > 0;
    }
}
