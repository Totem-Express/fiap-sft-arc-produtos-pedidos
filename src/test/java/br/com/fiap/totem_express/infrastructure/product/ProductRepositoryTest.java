package br.com.fiap.totem_express.infrastructure.product;

import br.com.fiap.totem_express.TestcontainersConfiguration;
import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import br.com.fiap.totem_express.presentation.product.request.CreateProductRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void should_delete_softly_product() {
        ProductEntity toNotBeDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("toNotBeDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));
        ProductEntity toBeDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("toBeDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));

        Long toBeDeletedId = toBeDeleted.toDomain().getId();
        productRepository.deleteById(toBeDeletedId);
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(1);
    }

    @Test
    @Transactional
    void should_return_not_deleted_products_by_category() {
        ProductEntity dishNotDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("dishNotDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));
        ProductEntity dishDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("toNotBeDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));
        ReflectionTestUtils.setField(dishDeleted, "deleted", true);
        productRepository.save(dishDeleted);
        ProductEntity sidedishNotDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("toNotBeDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.SIDE_DISH).toDomain()));
        List<ProductEntity> products = productRepository.findAllByCategory(Category.DISH);
        assertThat(products).hasSize(1).extracting(ProductEntity::toDomain).extracting(Product::getName).contains("dishNotDeleted");
    }

    @Test
    @Transactional
    void should_return_all_products_not_deleted_by_category() {
        ProductEntity dishNotDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("dishNotDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));
        ProductEntity dishDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("toNotBeDeleted", "descrição", "/image.png", BigDecimal.TEN, Category.DISH).toDomain()));
        ReflectionTestUtils.setField(dishDeleted, "deleted", true);
        productRepository.save(dishDeleted);
        ProductEntity sidedishNotDeleted = productRepository.save(new ProductEntity(new CreateProductRequest("sidDish", "descrição", "/image.png", BigDecimal.TEN, Category.SIDE_DISH).toDomain()));
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(2).extracting(ProductEntity::toDomain).extracting(Product::getCategory).contains(Category.DISH, Category.SIDE_DISH);
    }
}