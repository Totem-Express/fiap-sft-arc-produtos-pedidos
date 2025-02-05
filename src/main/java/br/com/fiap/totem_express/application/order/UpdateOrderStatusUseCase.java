package br.com.fiap.totem_express.application.order;

import br.com.fiap.totem_express.application.order.output.OrderView;

public interface UpdateOrderStatusUseCase {
    OrderView changeStatus(Long id);
}
