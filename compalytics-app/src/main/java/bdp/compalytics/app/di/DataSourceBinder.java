package bdp.compalytics.app.di;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

public class DataSourceBinder extends AbstractBinder {
    private final DataSource dataSource;

    public DataSourceBinder(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("database.url"));
        config.setUsername(properties.getProperty("database.username"));
        config.setPassword(properties.getProperty("database.password"));
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(6);
        config.setIdleTimeout(TimeUnit.SECONDS.toMillis(60));
        config.setConnectionTimeout(TimeUnit.SECONDS.toMillis(60));
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        this.dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    protected void configure() {
        bind(getDataSource()).to(DataSource.class);
    }
}
