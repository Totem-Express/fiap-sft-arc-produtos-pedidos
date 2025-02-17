package br.com.fiap.totem_express.presentation.product.request;

import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateProductRequestTest {


    @Test
    void should_return_correctly_to_domain() {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(42L, "produto", "descrição", "/image.png", BigDecimal.TEN, Category.DISH);
        Product domain = updateProductRequest.toDomain();
        assertThat(domain.getId()).isEqualTo(42);
        assertThat(domain.getName()).isEqualTo("produto");
        assertThat(domain.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(domain.getCategory()).isEqualTo(Category.DISH);
        assertThat(domain.getDescription()).isEqualTo("descrição");
        assertThat(domain.getImagePath()).isEqualTo("/image.png");
        assertThat(domain.getUpdatedAt()).isNotNull();
    }
}