package me.noynto.fortress.infrastructure.persistence;

import io.helidon.dbclient.DbClient;
import io.helidon.health.HealthCheck;
import io.helidon.health.HealthCheckResponse;
import io.helidon.health.HealthCheckType;

public class DatabaseHealthCheck implements HealthCheck {

    private final DbClient client;

    public DatabaseHealthCheck(DbClient client) {
        this.client = client;
    }

    @Override
    public HealthCheckType type() {
        return HealthCheckType.LIVENESS;
    }

    @Override
    public HealthCheckResponse call() {
        try {
            client.execute().createQuery("SELECT 1").execute();
            return HealthCheckResponse.builder().detail("provider", "persistence").status(HealthCheckResponse.Status.UP).build();
        } catch (Exception e) {
            return HealthCheckResponse.builder().detail("provider", "persistence").status(HealthCheckResponse.Status.DOWN).build();
        }
    }
}
