package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.application.payment.ProcessPaymentWebhookUseCase;
import br.com.fiap.totem_express.presentation.payment.request.PaymentWebhookRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentDocumentation {

    private final ProcessPaymentWebhookUseCase processPaymentWebhookUseCase;

    public PaymentController(ProcessPaymentWebhookUseCase processPaymentWebhookUseCase) {
        this.processPaymentWebhookUseCase = processPaymentWebhookUseCase;
    }

    @Override
    @Transactional
    @PostMapping("/api/payment/process/{id}")
    public ResponseEntity<Void> processPayment(String id, @RequestBody @Valid PaymentWebhookRequest input) {
        processPaymentWebhookUseCase.process(id, input);
        return ResponseEntity.ok().build();
    }
}
