package br.com.fiap.totem_express.application.order;

import br.com.fiap.totem_express.domain.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order create(Order domain);
    void changeStatus(Order current);

    Optional<Order> findByPaymentId(String paymentId);
}
