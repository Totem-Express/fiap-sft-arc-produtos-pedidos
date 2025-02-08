package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: teste
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private LocalDateTime updatedAt = LocalDateTime.now();

    @NotEmpty
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderItemEntity> items = new HashSet<>();

    @NotNull
    private BigDecimal total;


    private String user;

    @Enumerated(EnumType.STRING)
    private Status status = Status.RECEIVED;

    private String payment;

    @Deprecated
    public OrderEntity() {
    }

    public OrderEntity(Order order) {
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.total = order.getTotal();
        this.user = order.getPossibleUser().orElse(null);
        this.items = order.getItems().stream().map(item -> new OrderItemEntity(item, this)).collect(Collectors.toSet());
        this.payment = order.getPayment();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<OrderItemEntity> getItems() {
        return items;
    }

    public Order toDomain() {
        var order = new Order(
                id,
                createdAt,
                updatedAt,
                total,
                user,
                status,
                payment
        );

        final var orderItems = items.stream().map(item -> item.toDomain(order)).collect(Collectors.toSet());

        order.setItems(orderItems);

        return order;
    }
}
