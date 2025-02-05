package br.com.fiap.totem_express.application.payment;

import br.com.fiap.totem_express.application.payment.output.PaymentView;

public interface CheckPaymentStatusUseCase {
    PaymentView checkStatus(Long paymentId);
}