package me.noynto.fortress.infrastructure.persistence;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import org.flywaydb.core.Flyway;

public class DatabaseConfiguration {

    public DbClient client(Config dbConfig) {
        this.migrate(dbConfig.get("connection"));
        return DbClient.builder()
                .config(dbConfig)
                .build();
    }

    private void migrate(Config dbConfig) {
        Flyway
                .configure()
                .dataSource(dbConfig.get("url").asString().get(), null, null)
                .locations("classpath:db")
                .load()
                .migrate();

    }

}
