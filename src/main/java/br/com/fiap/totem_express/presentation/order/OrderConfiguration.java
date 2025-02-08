package br.com.fiap.totem_express.presentation.order;

import br.com.fiap.totem_express.application.order.CreateOrderUseCase;
import br.com.fiap.totem_express.application.order.ListOrderUseCase;
import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.order.UpdateOrderStatusUseCase;
import br.com.fiap.totem_express.application.order.impl.CreateOrderUseCaseImpl;
import br.com.fiap.totem_express.application.order.impl.ListOrderUseCaseImpl;
import br.com.fiap.totem_express.application.order.impl.UpdateOrderStatusUseCaseImpl;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.application.payment.PaymentProcessorGateway;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.infrastructure.order.OrderGatewayImpl;
import br.com.fiap.totem_express.infrastructure.order.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {
    private final OrderRepository repository;
    private final PaymentGateway paymentGateway;

    public OrderConfiguration(OrderRepository repository,  PaymentGateway paymentGateway) {
        this.repository = repository;
        this.paymentGateway = paymentGateway;
    }

    @Bean
    public OrderGateway orderGateway() {
        return new OrderGatewayImpl(repository, paymentGateway);
    }

    @Bean
    public ListOrderUseCase listOrderUseCase() {
        return new ListOrderUseCaseImpl(orderGateway());
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(ProductGateway productGateway, UserGateway userGateway, PaymentProcessorGateway paymentProcessorGateway) {
        return new CreateOrderUseCaseImpl(orderGateway(), productGateway, userGateway, paymentProcessorGateway);
    }
    
    @Bean
    public UpdateOrderStatusUseCase updateOrderStatusUseCase(){
        return new UpdateOrderStatusUseCaseImpl(orderGateway());
    }
}
