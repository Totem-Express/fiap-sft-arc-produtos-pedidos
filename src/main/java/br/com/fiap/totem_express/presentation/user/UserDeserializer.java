package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.LocalDateTime;

import static br.com.fiap.totem_express.shared.converters.LocalDateTimeFromArrayNode.buildLocalDateTime;

public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode node = p.getCodec().readTree(p);

        ObjectNode objectNode = (ObjectNode) node;
        if(objectNode.isEmpty()){
            return null;
        }
        String id = objectNode.get("id").asText();
        String name = objectNode.get("name").asText();
        String email = objectNode.get("email").asText();
        String cpf = objectNode.get("cpf").asText();
        Role role = Role.valueOf(objectNode.get("role").asText());
        LocalDateTime createdAt = buildLocalDateTime(objectNode.withArray("createdAt"));
        return new User(id, name, email, cpf, createdAt, role);
    }


}
