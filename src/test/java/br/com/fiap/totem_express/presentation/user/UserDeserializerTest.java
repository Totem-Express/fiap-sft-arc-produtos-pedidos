package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.presentation.CommonConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new CommonConfiguration().objectMapper();
    }

    @Test
    void should_return_null_if_user_is_empty() throws JsonProcessingException {
        String nullUser = "{}";
        User user = objectMapper.readValue(nullUser, User.class);
        assertThat(user).isNull();
    }

    @Test
    void should_build_user_correctly() throws JsonProcessingException {
        String id = "08846bb2-93ed-4fd1-98c9-eec62f51c3c8";
        String name = "name";
        String email = id + "@email.com";
        String cpf = "792.291.040-19";
        LocalDateTime createdAt = getFixedDate();
        Role admin = Role.ADMIN;
        User user = objectMapper.readValue("{\"name\":\"name\",\"email\":\"08846bb2-93ed-4fd1-98c9-eec62f51c3c8@email.com\",\"cpf\":\"792.291.040-19\",\"createdAt\":[2025,2,9,9,5,32,256],\"role\":\"ADMIN\",\"id\":\"08846bb2-93ed-4fd1-98c9-eec62f51c3c8\",\"document\":{\"value\":\"792.291.040-19\"}}", User.class);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getRole()).isEqualTo(admin);
        assertThat(user.getDocument().value()).isEqualTo(cpf);

    }

    private LocalDateTime getFixedDate(){
        int year = 2025;
        int month = 2;
        int dayOfTheMonth = 9;
        int hour = 9;
        int minute = 5 ;
        int seconds = 32;
        int milliseconds = 256;
        return LocalDateTime.of(year, month, dayOfTheMonth, hour, minute, seconds, milliseconds);
    }
}