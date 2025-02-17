package br.com.fiap.totem_express.infrastructure.payment;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.domain.payment.Status;
import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.infrastructure.order.OrderRepository;
import br.com.fiap.totem_express.infrastructure.product.ProductRepository;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayImpl;
import br.com.fiap.totem_express.presentation.CommonConfiguration;
import br.com.fiap.totem_express.presentation.order.OrderConfiguration;
import br.com.fiap.totem_express.presentation.payment.PaymentConfiguration;
import br.com.fiap.totem_express.presentation.product.ProductConfiguration;
import br.com.fiap.totem_express.presentation.user.UserConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@MockBeans({@MockBean(OrderRepository.class), @MockBean(ProductRepository.class), @MockBean(UserGateway.class)})
@RestClientTest({PaymentGatewayImpl.class, CommonConfiguration.class, PaymentConfiguration.class, OrderConfiguration.class, ProductConfiguration.class, UserConfiguration.class})
class PaymentGatewayImplTest {

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void should_find_payment_by_id() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        Payment payment = new Payment(uuid, createdAt,  createdAt, Status.PENDING, uuid, new BigDecimal("100.00"), uuid);
        mockServer.expect(requestTo("http://localhost:8083/api/id/"+uuid))
                .andRespond(withSuccess(objectMapper.writeValueAsString(payment), MediaType.APPLICATION_JSON));


        Optional<Payment> possiblePayment = paymentGateway.findById(uuid);
        assertThat(possiblePayment).isPresent();
        Payment retrievedPayment = possiblePayment.get();
        assertThat(retrievedPayment.getId()).isEqualTo(uuid);
        assertThat(retrievedPayment.getAmount()).isEqualTo(new BigDecimal("100.00"));
        assertThat(retrievedPayment.getStatus()).isEqualTo(Status.PENDING);
        assertThat(retrievedPayment.getCreatedAt()).isEqualTo(createdAt);
        assertThat(retrievedPayment.getUpdatedAt()).isEqualTo(createdAt);
        assertThat(retrievedPayment.getQrCode()).isEqualTo(uuid);
        assertThat(retrievedPayment.getTransactionId()).isEqualTo(uuid);
    }

    @Test
    void should_return_optional_empty_if_error_is_not_found() {
        String uuid = UUID.randomUUID().toString();
        mockServer.expect(requestTo("http://localhost:8083/api/id/"+uuid)).andRespond(withResourceNotFound());
        Optional<Payment> possiblePayment = paymentGateway.findById(uuid);
        assertThat(possiblePayment).isEmpty();
    }

    @Test
    void should_throw_exception_if_http_status_is_not_2xx() {
        Order order = mock(Order.class);
        mockServer.expect(requestTo("http://localhost:8083/api/create"))
                .andRespond(withBadRequest());
        assertThatThrownBy(() -> paymentGateway.create(order));
    }

    @Test
    void should_return_the_created_payment() throws JsonProcessingException {
        LocalDateTime createdAt = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();
        Payment payment = new Payment(uuid, createdAt,  createdAt, Status.PENDING, uuid, new BigDecimal("100.00"), uuid);
        mockServer.expect(requestTo("http://localhost:8083/api/create"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(payment), MediaType.APPLICATION_JSON));
        Order order = mock(Order.class);
        Payment returnedPayment = paymentGateway.create(order);

        assertThat(returnedPayment.getId()).isEqualTo(uuid);
        assertThat(returnedPayment.getAmount()).isEqualTo(new BigDecimal("100.00"));
        assertThat(returnedPayment.getStatus()).isEqualTo(Status.PENDING);
        assertThat(returnedPayment.getCreatedAt()).isEqualTo(createdAt);
        assertThat(returnedPayment.getUpdatedAt()).isEqualTo(createdAt);
        assertThat(returnedPayment.getQrCode()).isEqualTo(uuid);
        assertThat(returnedPayment.getTransactionId()).isEqualTo(uuid);
    }
}