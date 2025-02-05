package br.com.fiap.totem_express.presentation.order.requests;

import br.com.fiap.totem_express.application.order.input.OrderItemInput;

public record OrderItemRequest(Long productId, Long quantity)
        implements OrderItemInput {
}
