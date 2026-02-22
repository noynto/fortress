package me.noynto.fortress.domain.users;

import java.util.Objects;

public interface User {

    Id id();

    void id(Id id);

    ElectronicAddress electronicAddress();

    void electronicAddress(ElectronicAddress electronicAddress);

    Password password();

    void password(Password password);

    record Id(
            String value
    ) {
        public Id {
            Objects.requireNonNull(value);
        }
    }

    record ElectronicAddress(
            String value
    ) {
        public ElectronicAddress {
            Objects.requireNonNull(value);
        }
    }

    record Password(
            String value
    ) {
        public Password {
            Objects.requireNonNull(value);
        }
    }

    class Default implements User {
        private Id id;
        private ElectronicAddress electronicAddress;
        private Password password;

        @Override
        public Id id() {
            return this.id;
        }

        @Override
        public void id(Id id) {
            this.id = id;
        }

        @Override
        public ElectronicAddress electronicAddress() {
            return this.electronicAddress;
        }

        @Override
        public void electronicAddress(ElectronicAddress electronicAddress) {
            this.electronicAddress = electronicAddress;
        }

        @Override
        public Password password() {
            return this.password;
        }

        @Override
        public void password(Password password) {
            this.password = password;
        }
    }
}
