package br.com.fiap.totem_express.infrastructure.payment;

import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Optional;

public class PaymentGatewayImpl implements PaymentGateway {
    private final RestClient restClient;

    public PaymentGatewayImpl(RestClient.Builder restCleintBuilder, ObjectMapper objectMapper, String baseUrl) {
        restClient = restCleintBuilder.messageConverters(c -> {
            // Remove any existing MappingJackson2HttpMessageConverter
            c.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
            // Add MappingJackson2HttpMessageConverter
            c.add(new MappingJackson2HttpMessageConverter(objectMapper));
        }).baseUrl(baseUrl)
                .build();
    }

    @Override
    public Optional<Payment> findById(String id) {
        Payment payment = restClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode -> HttpStatusCode.valueOf(404).isSameCodeAs(httpStatusCode),
                        (request, response) -> {
                        })
                .body(Payment.class);
        return Optional.ofNullable(payment);
    }

    @Override
    public Payment create(Order order) {
        final var body = Map.of("amount", order.getTotal());
        return restClient.post()
                .uri("/create")
                .body(body)
                .retrieve()
                .body(Payment.class);
    }
}
