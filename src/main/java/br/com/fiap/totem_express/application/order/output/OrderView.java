package br.com.fiap.totem_express.application.order.output;

import br.com.fiap.totem_express.application.user.output.DefaultUserView;
import br.com.fiap.totem_express.domain.order.Order;
import br.com.fiap.totem_express.domain.order.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record OrderView(LocalDateTime createdAt, LocalDateTime updatedAt, Set<OrderItemView> items,
                        BigDecimal total, Status status, Long id, DefaultUserView possibleUserView) {
    public OrderView(Order order, Optional<DefaultUserView> possibleUser) {
        this(
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getItems().stream().map(OrderItemView::new).collect(Collectors.toSet()),
                order.getTotal(),
                order.getStatus(),
                order.getId(),
                possibleUser.orElse(null)
        );
    }

    public OrderView(Order order) {
        this(order, Optional.empty());
    }
}
