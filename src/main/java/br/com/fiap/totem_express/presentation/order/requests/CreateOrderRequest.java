package br.com.fiap.totem_express.presentation.order.requests;

import br.com.fiap.totem_express.application.order.input.CreateOrderInput;
import br.com.fiap.totem_express.application.order.input.OrderItemInput;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//TODO: teste
public record CreateOrderRequest(
        Set<OrderItemRequest> orderItemsRequest,
        Optional<String> possibleUserId
) implements CreateOrderInput {

    public CreateOrderRequest with(String userId){
        return new CreateOrderRequest(orderItemsRequest, Optional.of(userId));
    }

    @Override
    public Set<OrderItemInput> orderItems() {
        return orderItemsRequest() == null ? null : new HashSet<>(orderItemsRequest());
    }

    @Override
    public Optional<String> possibleUserId() {
        return possibleUserId;
    }

    @Override
    public Order toDomain(Set<OrderItem> orderItemsDomain, UserGateway userGateway) {
        return new Order(
                orderItemsDomain,
                possibleUserId
        );
    }
}
