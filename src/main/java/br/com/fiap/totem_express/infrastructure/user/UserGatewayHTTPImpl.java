package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

//TODO teste
//TODO autenticação entre serviços ?
public class UserGatewayHTTPImpl implements UserGateway {


    //TODO será que aqui usar o java comum para não se atrelar com o framework ia ser melhor?
    private final RestClient restClient;

    public UserGatewayHTTPImpl(){
        restClient = RestClient.create("http://localhost:8080/api/v1/users");//TODO pegar do properties isso
    }


    @Override
    public User create(User user) {
        return restClient
                .post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(User.class);
    }

    //TODO acho que não precisamos desse aqui no de produto/pedido
    @Override
    public List<User> findAll() {
        return restClient.get().retrieve().body(List.class);
    }

    //TODO acho que não precisamos desse aqui no de produto/pedido
    @Override
    public boolean existsById(Long id) {
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
    public boolean existsByEmailOrCPF(String email, String cpf) {
        final record SearchInput(String email, String cpf) {}// TODO melhorar
        return restClient
                .post()
                .uri("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new SearchInput(email, cpf))
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
    public Optional<User> findById(Long id) {
        User response = restClient.get()
                .uri("/id/{id}", id)
                .retrieve()
                .body(User.class);
        return Optional.ofNullable(response);
    }

    @Override
    public Optional<User> findByCPF(String cpf) {
        User response = restClient.get()
                .uri("/cpf/{id}", cpf)
                .retrieve()
                .body(User.class);
        return Optional.ofNullable(response);
    }
}
