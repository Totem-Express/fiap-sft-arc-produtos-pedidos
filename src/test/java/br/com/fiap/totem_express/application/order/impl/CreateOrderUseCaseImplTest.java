package br.com.fiap.totem_express.application.order.impl;

import br.com.fiap.totem_express.application.order.CreateOrderUseCase;
import br.com.fiap.totem_express.application.order.output.OrderView;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.payment.Payment;
import br.com.fiap.totem_express.domain.payment.Status;
import br.com.fiap.totem_express.domain.product.Product;
import br.com.fiap.totem_express.presentation.order.requests.CreateOrderRequest;
import br.com.fiap.totem_express.presentation.order.requests.OrderItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class CreateOrderUseCaseImplTest {

    private br.com.fiap.totem_express.application.order.OrderGateway orderGateway;
    private ProductGateway productGateway;
    private UserGateway userGateway;
    private PaymentGateway paymentGateway;
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderGateway = mock(br.com.fiap.totem_express.application.order.OrderGateway.class);
        productGateway = mock(ProductGateway.class);
        userGateway = mock(UserGateway.class);
        paymentGateway = mock(PaymentGateway.class);

        createOrderUseCase = new CreateOrderUseCaseImpl(orderGateway, productGateway, userGateway, paymentGateway);
    }

    @Test
    public void testExecute_validOrder() {
        OrderItemRequest validItem = new OrderItemRequest(1L, 2L);
        Set<OrderItemRequest> orderItemsRequest = Set.of(validItem);
        Order created = mock(Order.class);
        CreateOrderRequest orderInput = spy(new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com")));
        doReturn(created).when(orderInput).toDomain(any(), any());

        Product dummyProduct = mock(Product.class);
        when(dummyProduct.getPrice()).thenReturn(BigDecimal.TEN);
        when(productGateway.findById(1L)).thenReturn(Optional.of(dummyProduct));


        when(orderGateway.create(any(Order.class))).thenReturn(created);


        Payment payment = new Payment("100", LocalDateTime.now(), LocalDateTime.now(), Status.PENDING,
                "dummy-transaction", BigDecimal.valueOf(20), "dummy-qr-code");
        when(paymentGateway.create(any(Order.class))).thenReturn(payment);


        OrderView result = createOrderUseCase.execute(orderInput);

        verify(orderGateway).create(any(Order.class));
        verify(created).setPayment("100");
    }

    @Test
    public void testExecute_nullOrderItems() {
        CreateOrderRequest orderInput = new CreateOrderRequest(null, Optional.of("user@example.com"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );

        assertTrue(ex.getMessage().contains("Order must have items") ||
                   ex.getMessage().contains("not empty"),
                   "Expected error message about missing order items");
    }

    @Test
    public void testExecute_emptyOrderItems() {
        CreateOrderRequest orderInput = new CreateOrderRequest(Set.of(), Optional.of("user@example.com"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("Order must have items") ||
                   ex.getMessage().contains("not empty"),
                   "Expected error message about missing order items");
    }

    @Test
    public void testExecute_invalidOrderItem_nullProductId() {
        OrderItemRequest invalidItem = new OrderItemRequest(null, 2L);
        Set<OrderItemRequest> orderItemsRequest = Set.of(invalidItem);
        CreateOrderRequest orderInput = new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("positive productId"), "Expected error message about productId");
    }

    @Test
    public void testExecute_invalidOrderItem_nonPositiveProductId() {
        OrderItemRequest invalidItem = new OrderItemRequest(0L, 2L);
        Set<OrderItemRequest> orderItemsRequest = Set.of(invalidItem);
        CreateOrderRequest orderInput = new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("positive productId"), "Expected error message about productId");
    }

    @Test
    public void testExecute_invalidOrderItem_nullQuantity() {
        OrderItemRequest invalidItem = new OrderItemRequest(1L, null);
        Set<OrderItemRequest> orderItemsRequest = Set.of(invalidItem);
        CreateOrderRequest orderInput = new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com"));

        Product dummyProduct = mock(Product.class);
        when(dummyProduct.getPrice()).thenReturn(BigDecimal.TEN);
        when(productGateway.findById(1L)).thenReturn(Optional.of(dummyProduct));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("positive quantity"), "Expected error message about quantity");
    }

    @Test
    public void testExecute_invalidOrderItem_nonPositiveQuantity() {
        OrderItemRequest invalidItem = new OrderItemRequest(1L, 0L);
        Set<OrderItemRequest> orderItemsRequest = Set.of(invalidItem);
        CreateOrderRequest orderInput = new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com"));

        Product dummyProduct = mock(Product.class);
        when(dummyProduct.getPrice()).thenReturn(BigDecimal.TEN);
        when(productGateway.findById(1L)).thenReturn(Optional.of(dummyProduct));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("positive quantity"), "Expected error message about quantity");
    }

    @Test
    public void testExecute_productNotFound() {
        OrderItemRequest orderItem = new OrderItemRequest(1L, 2L);
        Set<OrderItemRequest> orderItemsRequest = Set.of(orderItem);
        CreateOrderRequest orderInput = new CreateOrderRequest(orderItemsRequest, Optional.of("user@example.com"));

        when(productGateway.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createOrderUseCase.execute(orderInput)
        );
        assertTrue(ex.getMessage().contains("Product must exists invalid id 1"),
                   "Expected error message about product not existing");
    }
}