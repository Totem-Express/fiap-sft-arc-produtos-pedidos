package br.com.fiap.totem_express.presentation.order;

import br.com.fiap.totem_express.application.order.CreateOrderUseCase;
import br.com.fiap.totem_express.application.order.ListOrderUseCase;
import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.order.UpdateOrderStatusUseCase;
import br.com.fiap.totem_express.application.order.impl.CreateOrderUseCaseImpl;
import br.com.fiap.totem_express.application.order.impl.ListOrderUseCaseImpl;
import br.com.fiap.totem_express.application.payment.PaymentProcessorGateway;
import br.com.fiap.totem_express.application.order.impl.UpdateOrderStatusUseCaseImpl;
import br.com.fiap.totem_express.application.product.ProductGateway;
import br.com.fiap.totem_express.application.user.*;
import br.com.fiap.totem_express.infrastructure.order.OrderGatewayImpl;
import br.com.fiap.totem_express.infrastructure.order.OrderRepository;
import br.com.fiap.totem_express.infrastructure.payment.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {
    private final OrderRepository repository;
    private final PaymentRepository paymentRepository;

    public OrderConfiguration(OrderRepository repository, PaymentRepository paymentRepository) {
        this.repository = repository;
        this.paymentRepository = paymentRepository;
    }

    @Bean
    public OrderGateway orderGateway() {
        return new OrderGatewayImpl(repository, paymentRepository);
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
