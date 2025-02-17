package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Optional;


public class UserGatewayImpl implements UserGateway {

    private final RestClient restClient;

    public UserGatewayImpl(RestClient.Builder restCleintBuilder, ObjectMapper objectMapper, String baseUrl) {
        restClient = restCleintBuilder.messageConverters(c -> {
            // Remove any existing MappingJackson2HttpMessageConverter
            c.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
            // Add MappingJackson2HttpMessageConverter
            c.add(new MappingJackson2HttpMessageConverter(objectMapper));
        }).baseUrl(baseUrl)
                .build();
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
        User user = restClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request,  response) -> {
                })
                .body(User.class);
        return Optional.ofNullable(user);
    }

}
