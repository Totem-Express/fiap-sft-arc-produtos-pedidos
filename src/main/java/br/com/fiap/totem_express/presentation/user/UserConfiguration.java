package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.impl.RetrieveUserUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayHTTPImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {


    @Bean
    public UserGateway userGateway(@Value("user-gateway-url") String userGatewayUrl) {
        return new UserGatewayHTTPImpl(userGatewayUrl);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserGateway userGateway) {
        return new RetrieveUserUseCaseImpl(userGateway);
    }


}
