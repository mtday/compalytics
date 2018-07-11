package bdp.compalytics.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import bdp.compalytics.db.DaoFactory;
import bdp.compalytics.db.SessionDao;
import bdp.compalytics.model.Session;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class JdbcSessionDaoTest {
    private static SessionDao sessionDao;

    @BeforeClass
    public static void setup() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + JdbcSessionDaoTest.class.getSimpleName() + ";DB_CLOSE_DELAY=-1");

        sessionDao = new DaoFactory(dataSource).getSessionDao();
    }

    @Test
    public void test() {
        Session session1 = new Session();
        session1.setId("session1");
        session1.setName("name1");
        session1.setUserId("user1");
        session1.setCreation(Instant.now());
        session1.setExpiration(Instant.now().plusSeconds(300));

        Session session2 = new Session();
        session2.setId("session2");
        session2.setName("name2");
        session2.setUserId("user2");
        session2.setCreation(Instant.now());
        session2.setExpiration(Instant.now().minusSeconds(300));

        sessionDao.add(session1);
        sessionDao.add(session2);

        Optional<Session> get = sessionDao.get(session1.getId());
        assertTrue(get.isPresent());
        assertEquals(session1, get.get());

        Optional<Session> getForUser = sessionDao.getForUser(session1.getUserId());
        assertTrue(getForUser.isPresent());
        assertEquals(session1, getForUser.get());

        List<Session> getAll = sessionDao.getAll();
        assertEquals(2, getAll.size());
        assertTrue(getAll.contains(session1));
        assertTrue(getAll.contains(session2));

        List<Session> getExpired = sessionDao.getExpired();
        assertEquals(1, getExpired.size());
        assertTrue(getExpired.contains(session2));

        session1.setName("updated");
        session1.setExpiration(Instant.now().plusSeconds(600));

        assertTrue(sessionDao.update(session1));

        Optional<Session> updated = sessionDao.get(session1.getId());
        assertTrue(updated.isPresent());
        assertEquals(session1, updated.get());

        assertTrue(sessionDao.delete(session1.getId()));
        assertTrue(sessionDao.deleteForUser(session2.getUserId()));
        assertFalse(sessionDao.delete("missing"));
        assertFalse(sessionDao.deleteForUser("missing"));

        assertFalse(sessionDao.get(session1.getId()).isPresent());
        assertFalse(sessionDao.get(session2.getId()).isPresent());
        assertTrue(sessionDao.getAll().isEmpty());
        assertTrue(sessionDao.getExpired().isEmpty());
    }
}
