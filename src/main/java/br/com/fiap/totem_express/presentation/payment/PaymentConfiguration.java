package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.application.payment.*;
import br.com.fiap.totem_express.application.payment.impl.CheckPaymentStatusUseCaseImpl;
import br.com.fiap.totem_express.application.payment.impl.ProcessPaymentWebhookUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.payment.*;
import br.com.fiap.totem_express.infrastructure.payment.mock.FakePaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PaymentConfiguration {

    private final PaymentRepository repository;
    private final RestTemplate restTemplate;

    public PaymentConfiguration(PaymentRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Bean
    public PaymentGateway paymentGateway() {
        return new PaymentGatewayImpl(repository);
    }

    @Bean
    public CheckPaymentStatusUseCase checkPaymentStatusUseCase() {
        return new CheckPaymentStatusUseCaseImpl(paymentGateway());
    }

    @Bean
    public ProcessPaymentWebhookUseCase processPaymentWebhookUseCase() {
        return new ProcessPaymentWebhookUseCaseImpl(paymentGateway());
    }

    @Bean
    PaymentProcessorGateway qrCodeGateway() {
        return new FakePaymentGateway();
    }
}
