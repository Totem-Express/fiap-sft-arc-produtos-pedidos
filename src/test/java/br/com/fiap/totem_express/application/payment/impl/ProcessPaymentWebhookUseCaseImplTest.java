package br.com.fiap.totem_express.application.payment.impl;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.input.PaymentWebhookInput;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.presentation.payment.request.PaymentWebhookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.totem_express.domain.payment.Status.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void should_throw_illegal_state_exception_when_order_not_exists() {
        String paymentId = UUID.randomUUID().toString();
        when(orderGateway.findByPaymentId(paymentId)).thenReturn(Optional.empty());
        assertThatIllegalStateException().isThrownBy(() -> useCase.process(paymentId, mock(PaymentWebhookInput.class)));
    }

    @Test
    void should_throw_illegal_state_exception_when_payment_not_exists() {
        String paymentId = UUID.randomUUID().toString();
        when(orderGateway.findByPaymentId(paymentId)).thenReturn(Optional.of(mock(Order.class)));
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.empty());
        assertThatIllegalStateException().isThrownBy(() -> useCase.process(paymentId, mock(PaymentWebhookInput.class)));
    }

    @Test
    void should_do_nothing_when_payment_is_pending() {
        String paymentId = UUID.randomUUID().toString();
        Order order = mock(Order.class);
        when(orderGateway.findByPaymentId(paymentId)).thenReturn(Optional.of(order));
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.of(mock(Payment.class)));
        PaymentWebhookInput paymentWebhookInput = mock(PaymentWebhookInput.class);
        when(paymentWebhookInput.status()).thenReturn(PENDING);
        useCase.process(paymentId, paymentWebhookInput);
        verifyNoInteractions(order);
        verify(orderGateway, never()).changeStatus(order);
    }

    @Test
    void should_go_to_next_status_when_payment_is_paid() {
        String paymentId = UUID.randomUUID().toString();
        Order order = mock(Order.class);
        when(orderGateway.findByPaymentId(paymentId)).thenReturn(Optional.of(order));
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.of(mock(Payment.class)));
        PaymentWebhookInput paymentWebhookInput = mock(PaymentWebhookInput.class);
        when(paymentWebhookInput.status()).thenReturn(PAID);
        useCase.process(paymentId, paymentWebhookInput);
        verify(order).goToNextStep();
        verify(orderGateway).changeStatus(order);
    }

    @Test
    void should_go_to_failed_when_payment_is_failed() {
        String paymentId = UUID.randomUUID().toString();
        Order order = mock(Order.class);
        when(orderGateway.findByPaymentId(paymentId)).thenReturn(Optional.of(order));
        when(paymentGateway.findById(paymentId)).thenReturn(Optional.of(mock(Payment.class)));
        PaymentWebhookInput paymentWebhookInput = mock(PaymentWebhookInput.class);
        when(paymentWebhookInput.status()).thenReturn(FAILED);
        useCase.process(paymentId, paymentWebhookInput);
        verify(order).failed();
        verify(orderGateway).changeStatus(order);
    }
}