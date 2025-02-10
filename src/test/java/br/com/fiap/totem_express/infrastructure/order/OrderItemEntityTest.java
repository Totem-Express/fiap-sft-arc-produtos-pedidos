package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;
import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import br.com.fiap.totem_express.infrastructure.product.ProductEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OrderItemEntityTest {

    @Test
    void should_create_order_item_entity_from_order_item() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "Product 1", "Description", "image/path.jpg", BigDecimal.TEN, Category.DISH);
        OrderItem orderItem = new OrderItem(1L, now, new ProductEntity(product), 2L);
        
        OrderItemEntity entity = new OrderItemEntity(orderItem);
        
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getQuantity()).isEqualTo(2L);
        assertThat(entity.getPrice()).isEqualTo(BigDecimal.TEN.multiply(BigDecimal.valueOf(2)));
    }

    @Test
    void should_create_order_item_entity_from_order_item_with_order() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "Product 1", "Description", "image/path.jpg", BigDecimal.TEN, Category.DRINK);
        OrderItem orderItem = new OrderItem(1L, now, new ProductEntity(product), 2L);
        OrderEntity orderEntity = new OrderEntity();
        
        OrderItemEntity entity = new OrderItemEntity(orderItem, orderEntity);
        
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getQuantity()).isEqualTo(2L);
        assertThat(entity.getPrice()).isEqualTo(BigDecimal.TEN.multiply(BigDecimal.valueOf(2)));
    }

    @Test
    void should_cornvert_to_domain() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "Product 1", "Description", "image/path.jpg", BigDecimal.TEN, Category.DESSERT);
        ProductEntity productEntity = new ProductEntity(product);
        
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1L);
        entity.setCreatedAt(now);
        entity.setProduct(productEntity);
        entity.setQuantity(2L);
        entity.setOrder(orderEntity);
        
        OrderItem domainItem = entity.toDomain();
        
        assertThat(domainItem.getId()).isEqualTo(1L);
        assertThat(domainItem.getCreatedAt()).isEqualTo(now);
        assertThat(domainItem.getQuantity()).isEqualTo(2L);
        assertThat(domainItem.getProduct().getId()).isEqualTo(1L);
        assertThat(domainItem.getOrder()).isNotNull();
        assertThat(domainItem.getOrder().getId()).isEqualTo(1L);
    }

    @Test
    void should_convert_to_domain_with_provided_order() {
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(1L, "Product 1", "Description", "image/path.jpg", BigDecimal.TEN, Category.SIDE_DISH);
        ProductEntity productEntity = new ProductEntity(product);
        
        Order order = mock(Order.class);
        
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1L);
        entity.setCreatedAt(now);
        entity.setProduct(productEntity);
        entity.setQuantity(2L);
        
        OrderItem domainItem = entity.toDomain(order);
        
        assertThat(domainItem.getId()).isEqualTo(1L);
        assertThat(domainItem.getCreatedAt()).isEqualTo(now);
        assertThat(domainItem.getQuantity()).isEqualTo(2L);
        assertThat(domainItem.getProduct().getId()).isEqualTo(1L);
        assertThat(domainItem.getOrder()).isEqualTo(order);
    }
} 