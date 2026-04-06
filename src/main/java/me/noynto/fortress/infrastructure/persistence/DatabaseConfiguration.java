package me.noynto.fortress.infrastructure.persistence;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbClientException;
import io.helidon.dbclient.jdbc.JdbcClientBuilder;
import io.helidon.dbclient.jdbc.JdbcConnectionPool;
import org.flywaydb.core.Flyway;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    public DbClient client(Config dbConfig) {
        Config connectionConfig = dbConfig.get("connection");
        String url = connectionConfig.get("url").asString().get();
        this.migrate(url);
        JdbcConnectionPool pool = () -> {
            try {
                return DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new DbClientException("Failed to get connection", e);
            }
        };
        return JdbcClientBuilder.create()
                .connectionPool(pool)
                .build();
    }

    private void migrate(String url) {
        Flyway
                .configure()
                .dataSource(url, null, null)
                .locations("classpath:db")
                .load()
                .migrate();

    }

}
