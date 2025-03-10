package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.TestcontainersConfiguration;
import br.com.fiap.totem_express.application.payment.ProcessPaymentWebhookUseCase;
import br.com.fiap.totem_express.infrastructure.jwt.JWTService;
import br.com.fiap.totem_express.presentation.payment.request.PaymentWebhookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static br.com.fiap.totem_express.domain.payment.Status.FAILED;
import static br.com.fiap.totem_express.domain.payment.Status.PAID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();


    @MockBean
    private ProcessPaymentWebhookUseCase processPaymentWebhookUseCase;


    @Test
    void should_return_http_200_when_payment_is_processed_successfully() throws Exception {
        String paymentId = UUID.randomUUID().toString();
        var request = new PaymentWebhookRequest(paymentId, PAID);

        doNothing().when(processPaymentWebhookUseCase).process(paymentId, request);

        mockMvc.perform(post("/api/payment/process/{id}", paymentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void should_return_http_404_when_payment_does_not_exist_during_processing() throws Exception {
        String paymentId = UUID.randomUUID().toString();

        var request = new PaymentWebhookRequest(paymentId, FAILED);

        doThrow(new IllegalArgumentException("Payment must exist invalid id " + paymentId))
                .when(processPaymentWebhookUseCase)
                .process(eq(paymentId), any());

        mockMvc.perform(post("/api/payment/process/{id}", paymentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isNotFound());
    }

}