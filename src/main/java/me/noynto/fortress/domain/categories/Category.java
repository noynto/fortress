package me.noynto.fortress.domain.categories;

import me.noynto.fortress.domain.shared.CategoryId;

import java.util.Objects;

public interface Category {

    record Name(
            String value
    ) {
        public Name {
            Objects.requireNonNull(value);
        }
    }

    CategoryId id();

    void id(CategoryId id);

    Name name();

    void name(Name name);

    class Default implements Category {
        private CategoryId id;
        private Name name;

        @Override
        public CategoryId id() {
            return id;
        }

        @Override
        public void id(CategoryId id) {
            this.id = id;
        }

        @Override
        public Name name() {
            return name;
        }

        @Override
        public void name(Name name) {
            this.name = name;
        }
    }

}
