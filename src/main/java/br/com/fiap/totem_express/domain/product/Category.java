package br.com.fiap.totem_express.domain.product;

import java.util.Optional;
import java.util.stream.Stream;

public enum Category {
    DRINK, DESSERT, DISH,  SIDE_DISH;

    public static Optional<Category> findByName(String name){
        return Stream.of(Category.values())
                .filter(category -> category.name().equalsIgnoreCase(name))
                .findFirst();
    }

}
