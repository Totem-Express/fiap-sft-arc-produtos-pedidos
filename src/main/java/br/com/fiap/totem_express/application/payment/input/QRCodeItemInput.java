package br.com.fiap.totem_express.application.payment.input;

import java.math.BigDecimal;

public interface QRCodeItemInput {
    String title();

    String description();

    BigDecimal unitPrice();

    Long quantity();

    String unitMeasure();

    BigDecimal totalAmount();
}
