package me.noynto.fortress.domain.categories;

import java.util.stream.Stream;

public interface CategoryProvider {

    Category create(Category.Name name);

    Stream<Category> stream();

}
