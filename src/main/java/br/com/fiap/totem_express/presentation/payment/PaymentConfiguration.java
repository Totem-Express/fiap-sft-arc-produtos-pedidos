package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.ProcessPaymentWebhookUseCase;
import br.com.fiap.totem_express.application.payment.impl.ProcessPaymentWebhookUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.payment.PaymentGatewayImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class PaymentConfiguration {


    public PaymentConfiguration() {
    }

    @Bean
    public PaymentGateway paymentGateway(RestClient.Builder restCleintBuilder, ObjectMapper objectMapper, @Value("${payment-service-base-url}") String baseUrl) {
        return new PaymentGatewayImpl(restCleintBuilder, objectMapper, baseUrl);
    }


    @Bean
    public ProcessPaymentWebhookUseCase processPaymentWebhookUseCase(OrderGateway orderGateway, PaymentGateway paymentGateway) {
        return new ProcessPaymentWebhookUseCaseImpl(orderGateway, paymentGateway);
    }
}
