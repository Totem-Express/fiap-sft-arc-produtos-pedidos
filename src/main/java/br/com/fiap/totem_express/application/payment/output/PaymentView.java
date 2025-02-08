package br.com.fiap.totem_express.application.payment.output;

import br.com.fiap.totem_express.domain.payment.Status;

public interface PaymentView {

    String id();

    Status status();

    String qrCode();

    record SimpleView(String id, Status status, String qrCode) implements PaymentView {
    }
}