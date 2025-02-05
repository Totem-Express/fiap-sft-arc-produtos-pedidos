package br.com.fiap.totem_express.application.order;

import br.com.fiap.totem_express.application.order.input.CreateOrderInput;
import br.com.fiap.totem_express.application.order.output.OrderView;

public interface CreateOrderUseCase {
    OrderView execute(CreateOrderInput orderInput);
}
