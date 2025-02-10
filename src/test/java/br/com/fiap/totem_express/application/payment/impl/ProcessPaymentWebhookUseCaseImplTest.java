package br.com.fiap.totem_express.application.payment.impl;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.input.PaymentWebhookInput;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.presentation.payment.request.PaymentWebhookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.totem_express.domain.payment.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessPaymentWebhookUseCaseImplTest {

    private PaymentGateway paymentGateway;

    private ProcessPaymentWebhookUseCaseImpl useCase;
    private OrderGateway orderGateway;

    @BeforeEach
    void setUp() {
        paymentGateway = mock(PaymentGateway.class);
        orderGateway = mock(OrderGateway.class);
        useCase = new ProcessPaymentWebhookUseCaseImpl(orderGateway, paymentGateway);
    }

    @Test
    void should_process_payment_for_paid_when_payment_exists() {
        String paymentId = UUID.randomUUID().toString();
        Payment payment = new Payment(
                paymentId,
                LocalDateTime.now(),
                LocalDateTime.now(),
                PENDING,
                "TXN123",
                new BigDecimal("100.00"),
                "QRCode123");

        when(paymentGateway.findById(paymentId)).thenReturn(Optional.of(payment));

        PaymentWebhookInput input = new PaymentWebhookRequest(paymentId, PAID);

        useCase.process(paymentId, input);

        assertThat(payment.getStatus()).isEqualTo(PAID);
    }

    @Test
    void should_return_exception_when_payment_does_not_exist() {
        String paymentId = UUID.randomUUID().toString();
        PaymentWebhookInput input = new PaymentWebhookRequest(paymentId, PENDING);

        when(paymentGateway.findById(paymentId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                useCase.process(paymentId, input)
        );

        assertThat(exception.getMessage()).isEqualTo("Payment must exists invalid id " + paymentId);
    }
}