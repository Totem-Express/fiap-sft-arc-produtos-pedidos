package br.com.fiap.totem_express.infrastructure.order;

import br.com.fiap.totem_express.domain.order.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
        SELECT order
        FROM orders order
        WHERE order.status != 'FINISHED'
        ORDER BY
            CASE
                WHEN order.status = 'READY_TO_BE_PICKED_UP' THEN 1
                WHEN order.status = 'PREPARING' THEN 2
                WHEN order.status = 'RECEIVED' THEN 3
                ELSE 4
            END,
            order.createdAt
    """)
    @EntityGraph(attributePaths = { "items", "user", "items.product", "payment" })
    List<OrderEntity> findAllWithDeletedProducts();


    @Modifying
    @Transactional
    @Query("UPDATE orders order SET order.status = :#{#order.status} WHERE order.id = :#{#order.id}")
    void updateStatus(Order order);

    Optional<Order> findByPayment(String paymentId);
}