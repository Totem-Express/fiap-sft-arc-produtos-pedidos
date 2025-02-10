package br.com.fiap.totem_express.application.order.impl;

import br.com.fiap.totem_express.application.order.CreateOrderUseCase;
import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.order.input.CreateOrderInput;
import br.com.fiap.totem_express.application.order.input.OrderItemInput;
import br.com.fiap.totem_express.application.order.output.OrderView;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.order.OrderItem;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.domain.product.Product;

import java.util.Set;
import java.util.stream.Collectors;


public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final UserGateway userGateway;
    private final PaymentGateway paymentGateway;

    public CreateOrderUseCaseImpl(OrderGateway orderGateway, ProductGateway productGateway,
                                  UserGateway userGateway, PaymentGateway paymentGateway) {
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
        this.userGateway = userGateway;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public OrderView execute(CreateOrderInput orderInput) {
        Set<OrderItemInput> orderItemInputs = orderInput.orderItems();
        validateOrderItems(orderItemInputs);

        Set<OrderItem> orderItemsDomain = orderItemInputs.stream().map(orderItemInput -> {
            validateOrderItem(orderItemInput);
            Product product = getProduct(orderItemInput);
            return new OrderItem(
                    product,
                    orderItemInput.quantity()
            );
        }).collect(Collectors.toSet());

        final var domain = orderInput.toDomain(orderItemsDomain, userGateway);

        final var created = orderGateway.create(domain);
        Payment payment = paymentGateway.create(created);
        domain.setPayment(payment.getId());
        return new OrderView(created);
    }

    private Product getProduct(OrderItemInput orderItemInput) {
        return productGateway.findById(orderItemInput.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product must exists invalid id " + orderItemInput.productId()));
    }

    private static void validateOrderItems(Set<OrderItemInput> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have items");
        }
    }

    private void validateOrderItem(OrderItemInput orderItemInput) {
        if (orderItemInput.productId() == null || orderItemInput.productId() <= 0) {
            throw new IllegalArgumentException("OrderItem must have a positive productId");
        }
        if (orderItemInput.quantity() == null || orderItemInput.quantity() <= 0) {
            throw new IllegalArgumentException("OrderItem must have a positive quantity");
        }
    }

}
