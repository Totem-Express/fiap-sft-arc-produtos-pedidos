package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Optional;

//TODO teste
public class UserGatewayHTTPImpl implements UserGateway {

    private final RestClient restClient;

    public UserGatewayHTTPImpl(String baseUrl) {
        restClient = RestClient.create(baseUrl);
    }


    @Override
    public boolean existsById(String id) {
        return restClient.get().uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((clientRequest, clientResponse) -> {
                    if(clientResponse.getStatusCode() == HttpStatus.OK){
                        return true;
                    }
                    if(clientResponse.getStatusCode() == HttpStatus.NOT_FOUND){
                        return false;
                    }
                    throw new RuntimeException("Problem communicating with user service");
                });
    }


    @Override
    public Optional<User> findById(String id) {
        User response = restClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .body(User.class);
        return Optional.ofNullable(response);
    }

}
