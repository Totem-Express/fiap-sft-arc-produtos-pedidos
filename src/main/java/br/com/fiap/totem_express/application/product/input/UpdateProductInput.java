package br.com.fiap.totem_express.application.product.input;

import java.math.BigDecimal;

import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;

public interface UpdateProductInput {

    Long id();

    String name();

    String description();

    String imagePath();

    BigDecimal price();

    Category category();

    Product toDomain();
}
