package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.impl.RetrieveUserUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class UserConfiguration {


    @Bean
    public UserGateway userGateway(RestClient.Builder builder, ObjectMapper objectMapper, @Value("${user-service-base-url}") String baseUrl) {
        return new UserGatewayImpl(builder, objectMapper, baseUrl);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserGateway userGateway) {
        return new RetrieveUserUseCaseImpl(userGateway);
    }


}
