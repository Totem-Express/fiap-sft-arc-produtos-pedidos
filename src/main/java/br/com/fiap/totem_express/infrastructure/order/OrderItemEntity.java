package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.OrderItem;
import br.com.fiap.totem_express.infrastructure.product.ProductEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime createdAt =  LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    private ProductEntity product;

    @Min(1)
    private Long quantity;

    @NotNull
    private BigDecimal price;

    @Deprecated
    public OrderItemEntity() {
    }

    public OrderItemEntity(OrderItem item) {
        this.id = item.getId();
        this.createdAt = item.getCreatedAt();
        this.product = new ProductEntity(item.getProduct());
        this.quantity = item.getQuantity();
        this.price = item.getTotal();
    }

    public OrderItemEntity(OrderItem item, OrderEntity orderEntity) {
        this(item);
        order = orderEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderItem toDomain() {
        Order orderDomain = order.toDomain();
        OrderItem orderItem = new OrderItem(id, createdAt, product, quantity);
        orderDomain.addItem(orderItem);
        orderItem.setOrder(orderDomain);
        return orderItem;
    }

    public OrderItem toDomain(Order order) {
        final var item = new OrderItem(id, createdAt, product, quantity);
        item.setOrder(order);
        return item;
    }
}
