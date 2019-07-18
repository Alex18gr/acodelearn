package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserJsonSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeString(user.getUserName());
        jgen.writeEndObject();
    }
}
