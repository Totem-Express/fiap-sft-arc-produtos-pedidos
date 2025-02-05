package br.com.fiap.totem_express.infrastructure.payment.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentProcessorResponse {

    @JsonProperty("qr_data")
    private String qrData;
    @JsonProperty("in_store_order_id")
    private String storeOrderId;

    public PaymentProcessorResponse(String qrData, String storeOrderId) {
        this.qrData = qrData;
        this.storeOrderId = storeOrderId;
    }

    public PaymentProcessorResponse() {
    }

    public String getQrData() {
        return qrData;
    }

    public void setQrData(String qrData) {
        this.qrData = qrData;
    }

    public String getStoreOrderId() {
        return storeOrderId;
    }

    public void setStoreOrderId(String storeOrderId) {
        this.storeOrderId = storeOrderId;
    }
}
