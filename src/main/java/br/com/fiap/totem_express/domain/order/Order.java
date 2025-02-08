package br.com.fiap.totem_express.domain.order;

import br.com.fiap.totem_express.shared.invariant.Invariant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.fiap.totem_express.shared.invariant.Rule.notEmpty;
import static br.com.fiap.totem_express.shared.invariant.Rule.notNull;


public class Order {
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Set<OrderItem> items = new HashSet<>();
    private BigDecimal total = BigDecimal.ZERO;
    private String user;
    private Status status = Status.RECEIVED;
    private String payment;

    public Order(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, BigDecimal total, String user, Status status, String payment) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.total = total;
        this.user = user;
        this.status = status;
        this.payment = payment;
        this.setTotal(items);
    }

    public Order(LocalDateTime createdAt, LocalDateTime updatedAt, Set<OrderItem> items, String user) {
        Invariant.of(createdAt, notNull("Order created at must be not null"));
        Invariant.of(updatedAt, notNull("Order updated at must be not null"));
        Invariant.of(items, notNull("Order item must be be not empty") ,notEmpty("Order item must be be not empty"));

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
        this.user = user;
        this.setTotal(items);
    }


    public Order(Set<OrderItem> orderItemsDomain, Optional<String> user) {
        this.user = user.orElse(null);
        orderItemsDomain.forEach(oi -> oi.setOrder(this));
        this.items = orderItemsDomain;
        this.setTotal(orderItemsDomain);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Optional<String> getPossibleUser() {
        return Optional.ofNullable(user);
    }

    public Status getStatus() {
        return status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


    public String getProductName() {
        return items.stream().map(OrderItem::getProductName).collect(Collectors.toSet()).toString();
    }

    public String getProductDescription() {
        return items.stream().map(OrderItem::getProductDescription).collect(Collectors.toSet()).toString();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    protected void setTotal(Set<OrderItem> items) {
        this.total = items.stream().map(OrderItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void goToNextStep() {
        this.status = status.next();
    }

    public void setItems(Set<OrderItem> orderItems) {
        this.items = orderItems;
        setTotal(items);
    }
}
