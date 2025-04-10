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

    public DBConnectionManger(String propertiesFilePath) {
        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(propertiesFilePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties file", e);
        }

        String jdbcUrl = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        int maxPoolSize = Integer.parseInt(properties.getProperty("jdbc.maxPoolSize", "2"));
        int minIdle = Integer.parseInt(properties.getProperty("jdbc.minIdle", "5"));
        long idleTimeout = Long.parseLong(properties.getProperty("jdbc.idleTimeout", "30000"));
        long maxLifetime = Long.parseLong(properties.getProperty("jdbc.maxLifetime", "600000"));
        long connectionTimeout = Long.parseLong(properties.getProperty("jdbc.connectionTimeout", "30000"));
        String connectionTestQuery = properties.getProperty("jdbc.connectionTestQuery", "SELECT 1 FROM DUAL");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionTestQuery(connectionTestQuery);

        this.dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            if (e instanceof java.sql.SQLTimeoutException) {
                throw new SQLException("Connection request timed out after "
                        + dataSource.getHikariConfigMXBean().getConnectionTimeout() + "ms.", e);
            } else {
                throw new SQLException("Failed to get connection from pool after waiting for "
                        + dataSource.getHikariConfigMXBean().getConnectionTimeout() + "ms.", e);
            }
        }
    }


    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
