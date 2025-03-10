package br.com.fiap.totem_express.application.payment;

import br.com.fiap.totem_express.application.payment.input.PaymentWebhookInput;

public interface ProcessPaymentWebhookUseCase {
    void process(String paymentId, PaymentWebhookInput input);
}