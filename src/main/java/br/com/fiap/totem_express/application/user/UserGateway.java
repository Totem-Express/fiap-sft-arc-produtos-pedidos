package br.com.fiap.totem_express.application.user;

import br.com.fiap.totem_express.domain.user.User;

import java.util.Optional;

public interface UserGateway {

    boolean existsById(String id);

    Optional<User> findById(String id);

}
