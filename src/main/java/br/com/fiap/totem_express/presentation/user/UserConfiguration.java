package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.CreateUserUseCase;
import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.impl.CreateUserUseCaseImpl;
import br.com.fiap.totem_express.application.user.impl.RetrieveUserUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayImpl;
import br.com.fiap.totem_express.infrastructure.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    private final UserRepository repository;

    public UserConfiguration(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public UserGateway userGateway(){
        return new UserGatewayImpl(repository);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(){
        return new RetrieveUserUseCaseImpl(userGateway());
    }

    @Bean
    public CreateUserUseCase createUserUseCase(){
        return new CreateUserUseCaseImpl(userGateway());
    }
}
