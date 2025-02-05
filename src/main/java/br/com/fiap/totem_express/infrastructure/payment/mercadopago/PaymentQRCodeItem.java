package br.com.fiap.totem_express.infrastructure.payment.mercadopago;

import br.com.fiap.totem_express.application.payment.input.QRCodeItemInput;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PaymentQRCodeItem(@JsonProperty("title") String title,
                                @JsonProperty("description") String description,
                                @JsonProperty("unit_price") BigDecimal unitPrice,
                                @JsonProperty("quantity") Long quantity,
                                @JsonProperty("unit_measure") String unitMeasure,
                                @JsonProperty("total_amount") BigDecimal totalAmount) implements QRCodeItemInput {
}
