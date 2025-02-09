package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.presentation.CommonConfiguration;
import br.com.fiap.totem_express.presentation.user.UserConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
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
class UserGatewayImplTest {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void should_check_if_user_exists_by_id() {
        String uuid = UUID.randomUUID().toString();
        mockServer.expect(requestTo("http://localhost:8082/api/"+uuid)).andRespond(withSuccess());
        boolean exists = userGateway.existsById(uuid);
        assertThat(exists).isTrue();
    }

    @Test
    void should_return_user_by_id() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();

        User user = new User(uuid, uuid, uuid + "@email.com", "792.291.040-19", LocalDateTime.now(), Role.ADMIN);
        mockServer.expect(requestTo("http://localhost:8082/api/id/"+uuid))
                .andRespond(withSuccess(objectMapper.writeValueAsString(user), MediaType.APPLICATION_JSON));


        Optional<User> possibleUser = userGateway.findById(uuid);
        assertThat(possibleUser).isPresent();
        User retrievedUser = possibleUser.get();
        assertThat(retrievedUser.getId()).isEqualTo(uuid);
        assertThat(retrievedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_return_empty_if_user_not_exists() {
        String notExistingId = UUID.randomUUID().toString();
        mockServer.expect(requestTo("http://localhost:8082/api/id/"+notExistingId)).andRespond(withResourceNotFound());

        Optional<User> possibleUser = userGateway.findById(notExistingId);
        assertThat(possibleUser).isEmpty();
    }
}