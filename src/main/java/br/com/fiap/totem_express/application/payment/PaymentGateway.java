package br.com.fiap.totem_express.application.payment;

import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;

import java.util.Optional;

public interface PaymentGateway {

    Optional<Payment> findById(String id);

    Payment create(Order order);
}
