package br.com.fiap.totem_express.application.order;

import br.com.fiap.totem_express.application.order.output.OrderView;

import java.util.List;

public interface ListOrderUseCase {
    List<OrderView> execute();
}
