package br.com.fiap.totem_express.presentation.order;

import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.product.Product;
import br.com.fiap.totem_express.presentation.order.requests.CreateOrderRequest;
import br.com.fiap.totem_express.presentation.order.requests.OrderItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateOrderValidatorTest {

    private CreateOrderValidator validator;
    private UserGateway userGateway;
    private ProductGateway productGateway;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        productGateway = mock(ProductGateway.class);
        validator = new CreateOrderValidator(userGateway, productGateway);
    }

    @Test
    void should_support_create_order_request_class() {
        assertThat(validator.supports(CreateOrderRequest.class)).isTrue();
    }

    @Test
    void should_not_support_other_classes() {
        assertThat(validator.supports(Object.class)).isFalse();
    }

    @Test
    void should_validate_successfully_when_all_products_exist_and_no_user() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        CreateOrderRequest request = new CreateOrderRequest(items, Optional.empty());
        Errors errors = new BeanPropertyBindingResult(request, "createOrderRequest");

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(productGateway.findAllByIds(Set.of(1L))).thenReturn(List.of(product));

        validator.validate(request, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void should_validate_successfully_when_all_products_and_user_exist() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        CreateOrderRequest request = new CreateOrderRequest(items, Optional.of("user123"));
        Errors errors = new BeanPropertyBindingResult(request, "createOrderRequest");

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(productGateway.findAllByIds(Set.of(1L))).thenReturn(List.of(product));
        when(userGateway.existsById("user123")).thenReturn(true);

        validator.validate(request, errors);

        assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    void should_reject_when_products_not_found() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        items.add(new OrderItemRequest(2L, 1L));
        CreateOrderRequest request = new CreateOrderRequest(items, Optional.empty());
        Errors errors = new BeanPropertyBindingResult(request, "createOrderRequest");

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(productGateway.findAllByIds(Set.of(1L, 2L))).thenReturn(List.of(product));

        validator.validate(request, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getAllErrors()).hasSize(1);
        assertThat(errors.getAllErrors().get(0).getCode()).isEqualTo("invalid.order.products");
        assertThat(errors.getAllErrors().get(0).getDefaultMessage())
            .isEqualTo("The following products 2 was not found.");
    }

    @Test
    void should_reject_when_user_not_found() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        CreateOrderRequest request = new CreateOrderRequest(items, Optional.of("nonexistent"));
        Errors errors = new BeanPropertyBindingResult(request, "createOrderRequest");

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(productGateway.findAllByIds(Set.of(1L))).thenReturn(List.of(product));
        when(userGateway.existsById("nonexistent")).thenReturn(false);

        validator.validate(request, errors);

        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getAllErrors()).hasSize(1);
        assertThat(errors.getAllErrors().get(0).getCode()).isEqualTo("invalid.order.user");
        assertThat(errors.getAllErrors().get(0).getDefaultMessage())
            .isEqualTo("User with id nonexistent not found");
    }

    @Test
    void should_return_early_when_errors_already_exist() {
        Set<OrderItemRequest> items = new HashSet<>();
        items.add(new OrderItemRequest(1L, 2L));
        CreateOrderRequest request = new CreateOrderRequest(items, Optional.empty());
        Errors errors = new BeanPropertyBindingResult(request, "createOrderRequest");
        errors.reject("pre.existing.error", "Pre-existing error");

        validator.validate(request, errors);

        verify(productGateway, never()).findAllByIds(any());
        verify(userGateway, never()).existsById(any());
    }
} 