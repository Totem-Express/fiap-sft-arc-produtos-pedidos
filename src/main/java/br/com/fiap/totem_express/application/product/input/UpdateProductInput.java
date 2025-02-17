package br.com.fiap.totem_express.application.product.input;

import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;

import java.math.BigDecimal;

public interface UpdateProductInput {

    Long id();

    String name();

    String description();

    String imagePath();

    BigDecimal price();

    Category category();

    Product toDomain();
}
