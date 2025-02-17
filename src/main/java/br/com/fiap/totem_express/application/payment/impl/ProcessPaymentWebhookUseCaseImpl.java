package br.com.fiap.totem_express.application.payment.impl;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.ProcessPaymentWebhookUseCase;
import br.com.fiap.totem_express.application.payment.input.PaymentWebhookInput;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.shared.invariant.InvariantException;

public class ProcessPaymentWebhookUseCaseImpl implements ProcessPaymentWebhookUseCase {

    private final OrderGateway orderGateway;
    private final PaymentGateway paymentGateway;

    public ProcessPaymentWebhookUseCaseImpl(OrderGateway gateway, PaymentGateway paymentGateway) {
        this.orderGateway = gateway;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void process(String paymentId, PaymentWebhookInput input) {
        Order order = orderGateway.findByPaymentId(paymentId).orElseThrow(() ->new IllegalStateException("Order not found"));
        paymentGateway.findById(paymentId).orElseThrow(() ->new IllegalStateException("Payment not found in gateway"));

        switch (input.status()){
            case PENDING -> {
            }
            case PAID -> {
                order.goToNextStep();
                orderGateway.changeStatus(order);
            }
            case FAILED ->{
                order.failed();
                orderGateway.changeStatus(order);
            }
        }

    }
}