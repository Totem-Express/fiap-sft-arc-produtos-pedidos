package br.com.fiap.totem_express.presentation;

import br.com.fiap.totem_express.application.payment.PaymentProcessorGateway;
import br.com.fiap.totem_express.infrastructure.payment.mock.FakePaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}