package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.application.order.OrderGateway;
import br.com.fiap.totem_express.application.payment.PaymentGateway;
import br.com.fiap.totem_express.domain.order.Order;

import java.util.List;
import java.util.Optional;

public class OrderGatewayImpl implements OrderGateway {

    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    public OrderGatewayImpl(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public void changeStatus(Order current) {
        orderRepository.updateStatus(current);
    }

    @Override
    public Optional<Order> findByPaymentId(String paymentId) {
        return orderRepository.findByPayment(paymentId);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(OrderEntity::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAllWithDeletedProducts().stream().map(orderEntity -> {
            Order orderDomain = orderEntity.toDomain();
            return orderDomain;
        }).toList();
    }

    @Override
    public Order create(Order domain) {
        OrderEntity savedOrderEntity = orderRepository.save(new OrderEntity(domain));
        Order savedOrderDomain = savedOrderEntity.toDomain();
        savedOrderEntity.getItems().forEach(orderItemEntity -> {
            savedOrderDomain.addItem(orderItemEntity.toDomain());
        });
        return savedOrderDomain;
    }

}
