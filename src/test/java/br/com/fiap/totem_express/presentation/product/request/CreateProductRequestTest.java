package br.com.fiap.totem_express.presentation.product.request;

import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CreateProductRequestTest {


    @Test
    void should_build_product_correctly() {
        CreateProductRequest createProductRequest = new CreateProductRequest( "produto", "descrição", "/image.png", BigDecimal.TEN, Category.DISH);
        Product domain = createProductRequest.toDomain();
        assertThat(domain.getId()).isNull();
        assertThat(domain.getUpdatedAt()).isNull();
        assertThat(domain.getCreatedAt()).isNotNull();
        assertThat(domain.getName()).isEqualTo("produto");
        assertThat(domain.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(domain.getCategory()).isEqualTo(Category.DISH);
        assertThat(domain.getDescription()).isEqualTo("descrição");
        assertThat(domain.getImagePath()).isEqualTo("/image.png");
    }
}