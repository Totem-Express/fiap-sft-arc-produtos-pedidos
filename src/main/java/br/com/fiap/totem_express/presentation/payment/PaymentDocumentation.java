package br.com.fiap.totem_express.presentation.payment;

import br.com.fiap.totem_express.presentation.payment.request.PaymentWebhookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pagamento", description = "API de pagamentos")
public interface PaymentDocumentation {

    @Operation(summary = "Processa confirmação de pagamento", description = "Recebe a confirmação de pagamento via webhook")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pagamento processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos estão incorretos"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema")
    })
    ResponseEntity<Void> processPayment(@PathVariable String id, @RequestBody @Valid PaymentWebhookRequest input);
}