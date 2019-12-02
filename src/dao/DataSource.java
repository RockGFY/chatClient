package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Mysql Connection Pool
 * This object requires the following third parties:
 *  1. HakiraCP 3.4.1
 *  2. slf4j-api-1.7.25
 *  3. slf4j-jdk14-1.7.25
 */
class DataSource {

    private static HikariDataSource dataSource;

    private static final String DB_NAME = "chatDb.db";

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:database/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(DB_URL);
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASS);
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
