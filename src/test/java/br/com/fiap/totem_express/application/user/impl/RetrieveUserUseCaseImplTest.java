package br.com.fiap.totem_express.application.user.impl;

import br.com.fiap.totem_express.TestcontainersConfiguration;
import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.output.DefaultUserView;
import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayImpl;
import br.com.fiap.totem_express.presentation.CommonConfiguration;
import br.com.fiap.totem_express.presentation.user.UserConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RestClientTest({UserGatewayImpl.class, CommonConfiguration.class, UserConfiguration.class})
class RetrieveUserUseCaseImplTest {


    @Autowired
    private RetrieveUserUseCase retrieveUserUseCase;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void execute_should_return_default_user_view_when_user_exists() throws JsonProcessingException {

        String uuid = UUID.randomUUID().toString();

        User hilaryOBrian = new User(uuid, uuid, uuid + "@email.com", "792.291.040-19", LocalDateTime.now(), Role.ADMIN);
        mockServer.expect(requestTo("http://localhost:8082/api/id/"+uuid))
                .andRespond(withSuccess(objectMapper.writeValueAsString(hilaryOBrian), MediaType.APPLICATION_JSON));

        var result = retrieveUserUseCase.execute(uuid);
        assertThat(result).isPresent();
        DefaultUserView defaultUserView = result.get();
        assertThat(defaultUserView.id()).isEqualTo(hilaryOBrian.getId());
        assertThat(defaultUserView.name()).isEqualTo(hilaryOBrian.getName());
    }

    @Test
    void execute_should_return_empty_optional_when_user_does_not_exist() {
        mockServer.expect(requestTo("http://localhost:8082/api/id/98765432100"))
                .andRespond(withResourceNotFound());
        var result = retrieveUserUseCase.execute("98765432100");

        assertThat(result).isEmpty();
    }
}