package com.and.utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManger {

    private HikariDataSource dataSource;
    private boolean enabled = false;

    public DBConnectionManger(String propertiesFilePath) {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            // File missing → DB disabled
            return;
        }

        String jdbcUrl = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        // ✅ Validate required properties
        if (isEmpty(jdbcUrl) || isEmpty(username) || isEmpty(password)) {
            return; // DB disabled silently
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        // Optional tuning with safe defaults
        config.setMaximumPoolSize(
                Integer.parseInt(properties.getProperty("jdbc.maxPoolSize", "2")));
        config.setMinimumIdle(
                Integer.parseInt(properties.getProperty("jdbc.minIdle", "1")));
        config.setIdleTimeout(
                Long.parseLong(properties.getProperty("jdbc.idleTimeout", "30000")));
        config.setMaxLifetime(
                Long.parseLong(properties.getProperty("jdbc.maxLifetime", "600000")));
        config.setConnectionTimeout(
                Long.parseLong(properties.getProperty("jdbc.connectionTimeout", "30000")));

        this.dataSource = new HikariDataSource(config);
        this.enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Connection getConnection() throws SQLException {
        if (!enabled || dataSource == null) {
            throw new SQLException("Database is not enabled. Check DB properties.");
        }
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
