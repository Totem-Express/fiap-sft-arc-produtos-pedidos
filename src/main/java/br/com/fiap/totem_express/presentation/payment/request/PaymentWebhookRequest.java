package br.com.fiap.totem_express.presentation.payment.request;

import br.com.fiap.totem_express.application.payment.input.PaymentWebhookInput;
import br.com.fiap.totem_express.domain.payment.Status;
import jakarta.validation.constraints.NotNull;

public record PaymentWebhookRequest(@NotNull Long id,
                                    @NotNull Status status)
        implements PaymentWebhookInput {
}
