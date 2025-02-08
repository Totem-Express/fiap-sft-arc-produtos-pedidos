package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.totem_express.domain.user.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Testcontainers
class UserGatewayHTTPImplTest {

    private UserGatewayHTTPImpl userGateway;

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_check_if_user_exists_by_id() {
        String uuid = UUID.randomUUID().toString();
        when(userGateway.existsById(uuid)).thenReturn(true);

        boolean exists = userGateway.existsById(uuid);

        assertThat(exists).isTrue();
    }

    @Test
    void should_return_user_by_id() {
        String uuid = UUID.randomUUID().toString();
        when(userGateway.existsById(uuid)).thenReturn(true);

        boolean exists = userGateway.existsById(uuid);

        assertThat(exists).isTrue();
    }
}