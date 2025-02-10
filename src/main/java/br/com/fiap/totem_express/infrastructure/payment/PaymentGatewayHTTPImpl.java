package br.com.fiap.totem_express.infrastructure.payment;

import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;

import java.util.Optional;

public class PaymentGatewayHTTPImpl implements PaymentGateway {

//    private final PaymentRepository repository;

    public PaymentGatewayHTTPImpl() {

    }

    @Override
    public Optional<Payment> findById(String id) {
        return Optional.empty();
//        return repository.findById(null).map(PaymentEntity::toDomain);//TODO fazer em outro pr para o http
    }

    @Override
    public Payment create(Order order) {
        return null;
//        PaymentEntity save = repository.save(new PaymentEntity(payment));
//        Payment domain = save.toDomain();
//        return domain;
    }
}
