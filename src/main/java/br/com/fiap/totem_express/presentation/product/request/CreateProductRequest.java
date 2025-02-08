package br.com.fiap.totem_express.presentation.product.request;

import br.com.fiap.totem_express.application.product.input.NewProductInput;
import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

//TODO: teste
public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String imagePath,
        @Positive BigDecimal price,
        @NotNull Category category) implements NewProductInput {

    @Override
    public Product toDomain() {
        return new Product(name, description, imagePath, price, category);
    }
}
