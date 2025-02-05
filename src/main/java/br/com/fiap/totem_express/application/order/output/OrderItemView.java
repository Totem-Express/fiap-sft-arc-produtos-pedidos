package br.com.fiap.totem_express.application.order.output;

import br.com.fiap.totem_express.domain.order.OrderItem;

import java.math.*;

public record OrderItemView(String name, Long quantity, BigDecimal price) {
    public OrderItemView(OrderItem item) {
        this(item.getProductName(), item.getQuantity(), item.getTotal());
    }
}
