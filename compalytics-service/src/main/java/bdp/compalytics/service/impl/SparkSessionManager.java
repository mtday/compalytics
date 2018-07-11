package bdp.compalytics.service.impl;

import bdp.compalytics.model.Session;
import bdp.compalytics.service.SessionManager;
import org.apache.spark.sql.SparkSession;

public class SparkSessionManager implements SessionManager {
    @Override
    public boolean create(Session session) {
        SparkSession.builder()
                .master("yarn-client")
                .appName("Compalytics:" + session.getUserId())
                .getOrCreate();
        return true;
    }

    @Override
    public boolean close(Session session) {
        SparkSession.builder()
                .master("yarn-client")
                .appName("Compalytics:" + session.getUserId())
                .getOrCreate()
                .sparkContext()
                .cancelAllJobs();
        return true;
    }
}
