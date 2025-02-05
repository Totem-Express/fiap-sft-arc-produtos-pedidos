package br.com.fiap.totem_express.application.payment.input;

import br.com.fiap.totem_express.domain.payment.Status;

public interface PaymentWebhookInput {
    Status status();
}