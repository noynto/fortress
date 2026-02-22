package me.noynto.fortress.domain.users;

import java.util.Objects;

public interface Session {

    Id id();

    User.Id userId();

    record Id(
            String value
    ) {
        public Id {
            Objects.requireNonNull(value);
        }
    }
}
