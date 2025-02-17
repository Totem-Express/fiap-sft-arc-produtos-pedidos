package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;
import br.com.fiap.totem_express.domain.order.Status;
import br.com.fiap.totem_express.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderEntityTest {

    @Test
    void should_create_order_entity_from_order() {
        LocalDateTime now = LocalDateTime.now();
        Set<OrderItem> items = new HashSet<>();
        OrderItem orderItem = mock(OrderItem.class);
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(orderItem.getProduct()).thenReturn(product);
        when(orderItem.getTotal()).thenReturn(BigDecimal.TWO);
        items.add(orderItem);
        
        Order order = new Order(1L, now, now, BigDecimal.TEN, "user123", Status.RECEIVED, "payment123");
        order.setItems(items);
        
        OrderEntity entity = new OrderEntity(order);
        
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCreatedAt()).isEqualTo(now);
        assertThat(entity.getItems()).isNotEmpty();
    }

    @Test
    void should_convert_to_domain_object() {
        LocalDateTime now = LocalDateTime.now();
        OrderEntity entity = new OrderEntity();
        entity.setId(1L);

        Set<OrderItemEntity> items = new HashSet<>();
        OrderItemEntity itemEntity = mock(OrderItemEntity.class);
        OrderItem orderItem = mock(OrderItem.class);
        when(itemEntity.toDomain(any(Order.class))).thenReturn(orderItem);
        items.add(itemEntity);
        when(orderItem.getTotal()).thenReturn(BigDecimal.TEN);
        ReflectionTestUtils.setField(entity, "items", items);
        Order domain = entity.toDomain();
        
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getStatus()).isEqualTo(Status.RECEIVED);
        assertThat(domain.getItems()).contains(orderItem);
        assertThat(domain.getTotal()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void should_handle_null_user_in_constructor() {
        LocalDateTime now = LocalDateTime.now();
        Set<OrderItem> items = new HashSet<>();
        Order order = new Order(1L, now, now, BigDecimal.TEN, null, Status.RECEIVED, "payment123");
        order.setItems(items);
        
        OrderEntity entity = new OrderEntity(order);
        
        assertThat(entity.toDomain().getPossibleUser()).isEmpty();
    }

    @Test
    void should_set_and_get_id() {
        OrderEntity entity = new OrderEntity();
        entity.setId(42L);
        
        assertThat(entity.getId()).isEqualTo(42L);
    }

    @Test
    void should_get_items() {
        OrderEntity entity = new OrderEntity();
        Set<OrderItemEntity> items = new HashSet<>();
        items.add(mock(OrderItemEntity.class));
        
        ReflectionTestUtils.setField(entity, "items", items);
        
        assertThat(entity.getItems()).isEqualTo(items);
    }
} 