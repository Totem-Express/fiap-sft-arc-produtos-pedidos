package br.com.fiap.totem_express.presentation.order;

import br.com.fiap.totem_express.application.order.output.OrderView;
import br.com.fiap.totem_express.presentation.errors.BadRequestError;
import br.com.fiap.totem_express.presentation.order.requests.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pedido", description = "API de pedidos")
public interface OrderDocumentation {

    @Operation(summary = "Lista pedidos cadastrados", description = "Retorna todos os pedidos cadastrados sem diferenciação de status ou cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OrderView.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "Quando não tem pedidos cadastrados",content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema", content = {@Content(schema = @Schema())})
    })
    ResponseEntity list();

    @Operation(summary = "Cria um pedido", description = "Cria um pedido e retorna ele como salvo")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Com os valores do pedido criado", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OrderView.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Erro de validação do Usuário ou Produto", content = {@Content(schema = @Schema(implementation = BadRequestError.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema", content = {@Content(schema = @Schema())})
    })
    ResponseEntity create(CreateOrderRequest createOrderRequest);

    @Operation(summary = "Atualiza Status", description = "Muda o status do pedido para o próximo")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Status atualizado com sucesso", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OrderView.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema", content = {@Content(schema = @Schema())})
    })
    ResponseEntity goToNextStatus(Long id);
}
