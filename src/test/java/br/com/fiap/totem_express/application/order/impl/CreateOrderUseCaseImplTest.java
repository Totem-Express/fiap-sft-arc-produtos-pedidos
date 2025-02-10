package br.com.fiap.totem_express.application.order.impl;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.order.input.CreateOrderInput;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.presentation.order.requests.OrderItemRequest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO arrumar esses testes
class CreateOrderUseCaseImplTest {

    @Test
    void should_execute_order_successfully() {
        OrderGateway orderGateway = mock(OrderGateway.class);
        ProductGateway productGateway = mock(ProductGateway.class);
        UserGateway userGateway = mock(UserGateway.class);
//        //TODO ajustar esses métodos
//        //PaymentProcessorGateway qrCodeGateway = mock(PaymentProcessorGateway.class);
//
//        Product product = new Product(1L, "Product 1", "Description 1", "image.png", BigDecimal.valueOf(10.0), Category.DISH);
//        Order order = new Order(Set.of(new OrderItem(1L, LocalDateTime.now(), new ProductEntity(product), 1L)), empty());
//        ReflectionTestUtils.setField(order, "id", 1L);
//        order.setPayment("PAYMENT_ID");//TODO depois ver como fazer direitinho
//
//        when(productGateway.findById(1L)).thenReturn(Optional.of(product));
//        when(orderGateway.create(any(Order.class))).thenReturn(order);
////        PaymentProcessorResponse paymentProcessorResponse = mock(PaymentProcessorResponse.class);
////        when(qrCodeGateway.createPaymentQRCode(any(GenerateQRCodeInput.class))).thenReturn(paymentProcessorResponse);
//
////        CreateOrderUseCaseImpl useCase = new CreateOrderUseCaseImpl(orderGateway, productGateway, userGateway, qrCodeGateway);
//
//        CreateOrderRequest orderInput = new CreateOrderRequest(order.getItems().stream().map(it -> new OrderItemRequest(it.getId(), it.getQuantity())).collect(Collectors.toSet()), empty());
//        OrderView result = useCase.execute(orderInput);
//
//        assertThat(result).isNotNull();
//        assertThat(result.id()).isEqualTo(order.getId());
//        assertThat(result.total()).isEqualTo(order.getTotal());
    }

    @Test
    void should_throw_exception_when_order_has_no_items() {
//        OrderGateway orderGateway = mock(OrderGateway.class);
//        ProductGateway productGateway = mock(ProductGateway.class);
//        UserGateway userGateway = mock(UserGateway.class);
////        PaymentProcessorGateway qrCodeGateway = mock(PaymentProcessorGateway.class);
//
//        CreateOrderInput mockInput = mock(CreateOrderInput.class);
//        when(mockInput.orderItems()).thenReturn(Set.of());
//
//        CreateOrderUseCaseImpl useCase = new CreateOrderUseCaseImpl(orderGateway, productGateway, userGateway, qrCodeGateway);
//
//        assertThatThrownBy(() -> useCase.execute(mockInput))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("Order must have items");
    }

    @Test
    void should_throw_exception_for_invalid_product_id() {
        OrderGateway orderGateway = mock(OrderGateway.class);
        ProductGateway productGateway = mock(ProductGateway.class);
        UserGateway userGateway = mock(UserGateway.class);
//        PaymentProcessorGateway qrCodeGateway = mock(PaymentProcessorGateway.class);

        CreateOrderInput mockInput = mock(CreateOrderInput.class);
        when(mockInput.orderItems()).thenReturn(Set.of(new OrderItemRequest(-1L, 1L)));
//
//        CreateOrderUseCaseImpl useCase = new CreateOrderUseCaseImpl(orderGateway, productGateway, userGateway, qrCodeGateway);
//
//        assertThatThrownBy(() -> useCase.execute(mockInput))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("OrderItem must have a positive productId");
    }

    @Test
    void should_throw_exception_when_product_not_found() {
//        OrderGateway orderGateway = mock(OrderGateway.class);
//        ProductGateway productGateway = mock(ProductGateway.class);
//        UserGateway userGateway = mock(UserGateway.class);
////        PaymentProcessorGateway qrCodeGateway = mock(PaymentProcessorGateway.class);
//
//        CreateOrderInput mockInput = mock(CreateOrderInput.class);
//        when(mockInput.orderItems()).thenReturn(Set.of(new OrderItemRequest(99L, 1L)));
//        when(productGateway.findById(99L)).thenReturn(empty());
//
////        CreateOrderUseCaseImpl useCase = new CreateOrderUseCaseImpl(orderGateway, productGateway, userGateway, qrCodeGateway);
//
////        assertThatThrownBy(() -> useCase.execute(mockInput))
////                .isInstanceOf(IllegalArgumentException.class)
////                .hasMessage("Product must exists invalid id 99");
    }
}