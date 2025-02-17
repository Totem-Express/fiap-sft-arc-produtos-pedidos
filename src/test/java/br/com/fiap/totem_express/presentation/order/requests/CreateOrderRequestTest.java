package br.com.fiap.totem_express.presentation.order.requests;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateOrderRequestTest {

    @Test
    void should_create_with_null_items_and_empty_user() {
        CreateOrderRequest request = new CreateOrderRequest(null, Optional.empty());
        
        assertThat(request.orderItemsRequest()).isNull();
        assertThat(request.possibleUserId()).isEmpty();
        assertThat(request.orderItems()).isNull();
    }

    @Test
    void should_create_with_items_and_user() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        Optional<String> userId = Optional.of("user123");
        
        CreateOrderRequest request = new CreateOrderRequest(items, userId);
        
        assertThat(request.orderItemsRequest()).isEqualTo(items);
        assertThat(request.possibleUserId()).isEqualTo(userId);
        assertThat(request.orderItems())
            .isNotNull()
            .hasSize(1)
            .allMatch(item -> item instanceof OrderItemRequest);
    }

    @Test
    void with_should_create_new_instance_with_updated_user_id() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        CreateOrderRequest originalRequest = new CreateOrderRequest(items, Optional.empty());
        
        CreateOrderRequest updatedRequest = originalRequest.with("newUser123");
        
        assertThat(updatedRequest)
            .isNotSameAs(originalRequest)
            .extracting(
                CreateOrderRequest::orderItemsRequest,
                CreateOrderRequest::possibleUserId
            )
            .containsExactly(
                items,
                Optional.of("newUser123")
            );
    }

    @Test
    void toDomain_should_create_order_with_items_and_user() {
        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getTotal()).thenReturn(BigDecimal.ONE);
        orderItems.add(orderItem);
        
        UserGateway userGateway = mock(UserGateway.class);
        CreateOrderRequest request = new CreateOrderRequest(null, Optional.of("user123"));
        
        Order order = request.toDomain(orderItems, userGateway);
        
        assertThat(order)
            .isNotNull()
            .extracting(
                Order::getItems,
                it -> it.getPossibleUser()
            )
            .containsExactly(
                orderItems,
                Optional.of("user123")
            );
    }
} 