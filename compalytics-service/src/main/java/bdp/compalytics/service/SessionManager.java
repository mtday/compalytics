package bdp.compalytics.service;

import bdp.compalytics.model.Session;

public interface SessionManager {
    boolean create(Session session);
    boolean close(Session session);
}
