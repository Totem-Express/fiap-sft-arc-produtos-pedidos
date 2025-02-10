package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.ProcessPaymentWebhookUseCase;
import br.com.fiap.totem_express.application.payment.impl.ProcessPaymentWebhookUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.payment.PaymentGatewayHTTPImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {


    public PaymentConfiguration() {
    }

    @Bean
    public PaymentGateway paymentGateway() {
        return new PaymentGatewayHTTPImpl();
    }


    @Bean
    public ProcessPaymentWebhookUseCase processPaymentWebhookUseCase(OrderGateway orderGateway) {
        return new ProcessPaymentWebhookUseCaseImpl(orderGateway, paymentGateway());
    }
}
