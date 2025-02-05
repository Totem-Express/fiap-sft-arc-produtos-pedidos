package br.com.fiap.totem_express.application.order;

import br.com.fiap.totem_express.domain.order.*;

import java.util.*;

public interface OrderGateway {
    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order create(Order domain);
    void changeStatus(Order current);
}
