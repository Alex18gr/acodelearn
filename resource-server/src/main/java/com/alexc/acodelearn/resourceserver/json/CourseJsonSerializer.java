package com.alexc.acodelearn.resourceserver.json;

import com.alexc.acodelearn.resourceserver.entity.Course;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class CourseJsonSerializer extends JsonSerializer<Course> {
    @Override
    public void serialize(Course course, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumber(course.getId());
        jgen.writeString(course.getTitle());
        jgen.writeEndObject();
    }
}
