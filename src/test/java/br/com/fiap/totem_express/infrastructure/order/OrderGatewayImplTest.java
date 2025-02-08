package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.domain.product.Category;
import br.com.fiap.totem_express.domain.product.Product;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.infrastructure.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.fiap.totem_express.domain.order.Status.RECEIVED;
import static br.com.fiap.totem_express.domain.user.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderGatewayImplTest {

    private OrderRepository orderRepository;
    private PaymentGateway paymentGateway;
    private OrderGatewayImpl orderGateway;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        paymentGateway = mock(PaymentGateway.class);
        orderGateway = new OrderGatewayImpl(orderRepository, paymentGateway);
    }

    @Test
    void should_change_status() {
        Order order = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.TEN, null, RECEIVED, null);
        doNothing().when(orderRepository).updateStatus(order);
        orderGateway.changeStatus(order);

        verify(orderRepository).updateStatus(order);
    }

    @Test
    void should_find_order_by_id() {
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
        when(orderEntity.toDomain()).thenReturn(new Order(1L, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.TEN, null, RECEIVED, null));

        Optional<Order> result = orderGateway.findById(1L);

        assertThat(result).isPresent();
        verify(orderRepository).findById(1L);
    }

    @Test
    void should_find_all_orders() {
        OrderEntity orderEntity = mock(OrderEntity.class);
        when(orderRepository.findAllWithDeletedProducts()).thenReturn(List.of(orderEntity));
        when(orderEntity.toDomain()).thenReturn(new Order(1L, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.TEN, null, RECEIVED, null));

        List<Order> result = orderGateway.findAll();

        assertThat(result).hasSize(1);
        verify(orderRepository).findAllWithDeletedProducts();
    }

    @Test
    void should_create_order_with_payment() {
        Product product = new Product(1L, "Product 1", "Description 1", "image.png", BigDecimal.valueOf(10.0), Category.DISH);
        OrderItem orderItem = new OrderItem(1L, LocalDateTime.now(), new ProductEntity(product), 1L);
        Order order = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.TEN, "1L", RECEIVED, "1L");
        order.setItems(Set.of(orderItem));

        OrderEntity orderEntity = mock(OrderEntity.class);

        when(paymentGateway.findById("1L")).thenReturn(Optional.ofNullable(mock(Payment.class)));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderEntity.toDomain()).thenReturn(order);

        Order result = orderGateway.create(order);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void should_create_order_without_payment() {
        Product product = new Product(1L, "Product 1", "Description 1", "image.png", BigDecimal.valueOf(10.0), Category.DISH);
        OrderItem orderItem = new OrderItem(1L, LocalDateTime.now(), new ProductEntity(product), 1L);
        Order order = new Order(1L, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.TEN, "1L", RECEIVED, null);
        order.setItems(Set.of(orderItem));

        OrderEntity orderEntity = mock(OrderEntity.class);

        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        when(orderEntity.toDomain()).thenReturn(order);

        Order result = orderGateway.create(order);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(orderRepository).save(any(OrderEntity.class));
    }

}