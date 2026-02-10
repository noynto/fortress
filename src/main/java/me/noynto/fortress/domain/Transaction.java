package me.noynto.fortress.domain;

import java.math.BigDecimal;
import java.util.Objects;

public interface Transaction {


    record Id(
            String value
    ) {
        public Id {
            Objects.requireNonNull(value);
        }
    }

    record Description(
            String value
    ) {
        public Description {
            Objects.requireNonNull(value);
        }
    }

    record Amount(
            BigDecimal value
    ) {
        public Amount {
            Objects.requireNonNull(value);
        }
    }

    enum Type {
        CREDIT,
        DEBIT
    }

    enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    class NotFindable extends Exception {
        public NotFindable(String message) {
            super(message);
        }
    }

    Id id();

    void id(Id id);

    Description description();

    void description(Description description);

    Amount amount();

    void amount(Amount amount);

    Type type();

    void type(Type type);

    Status status();

    void status(Status status);

    class Default implements Transaction {
        private Id id;
        private Description description;
        private Amount amount;
        private Type type;
        private Status status;

        @Override
        public Id id() {
            return this.id;
        }

        @Override
        public void id(Id id) {
            this.id = id;
        }

        @Override
        public Description description() {
            return this.description;
        }

        @Override
        public void description(Description description) {
            this.description = description;
        }

        @Override
        public Amount amount() {
            return this.amount;
        }

        @Override
        public void amount(Amount amount) {
            this.amount = amount;
        }

        @Override
        public Type type() {
            return this.type;
        }

        @Override
        public void type(Type type) {
            this.type = type;
        }

        @Override
        public Status status() {
            return this.status;
        }

        @Override
        public void status(Status status) {
            this.status = status;
        }
    }
}
