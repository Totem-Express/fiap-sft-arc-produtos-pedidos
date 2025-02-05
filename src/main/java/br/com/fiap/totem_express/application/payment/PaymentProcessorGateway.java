package br.com.fiap.totem_express.application.payment;

import br.com.fiap.totem_express.application.payment.input.GenerateQRCodeInput;
import br.com.fiap.totem_express.infrastructure.payment.mercadopago.PaymentProcessorResponse;

public interface PaymentProcessorGateway {
    PaymentProcessorResponse createPaymentQRCode(GenerateQRCodeInput input);
}
