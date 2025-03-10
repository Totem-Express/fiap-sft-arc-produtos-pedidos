package br.com.fiap.totem_express.application.product.output;

import br.com.fiap.totem_express.domain.product.Category;

import java.math.BigDecimal;

public interface ProductView {

    Long id();

    String name();

    String description();

    String imagePath();

    BigDecimal price();

    Category category();

    record SimpleView(
            Long id,
            String name,
            String description,
            String imagePath,
            BigDecimal price,
            Category category) implements ProductView {
    }
}
