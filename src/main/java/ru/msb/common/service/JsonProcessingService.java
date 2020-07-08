package ru.msb.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.apptik.json.JsonElement;
import io.apptik.json.generator.Generator;
import io.apptik.json.schema.SchemaV4;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;

import static java.lang.String.format;

@Slf4j
@Service
public class JsonProcessingService {

    private final ObjectMapper MAPPER = new ObjectMapper();

    public String toString(JsonNode json) {
        return Try.of(() -> MAPPER.writeValueAsString(json))
                .get();
    }

    public JsonNode toJson(String json) {
        return Try.of(() -> MAPPER.readTree(json))
                .get();
    }

    public JsonSchema generateSchemaFromClass(Class<?> clazz) {
        log.debug("Начата генерация схемы для класса: {}", clazz.getName());
        return Try.of(() -> new JsonSchemaGenerator(MAPPER)
                .generateSchema(clazz))
                .get();
    }

    public JsonNode readSchema(String fileName) {
        return Try.of(() -> MAPPER.readTree(
                Try.of(() -> new FileReader(format("src\\main\\resources\\jsonSchems\\%s", fileName))
                ).get())
        ).get();
    }

    public String generateJsonFromSchema(String schemaAsString) {
        return Try.of(() -> new Generator(
                new SchemaV4().wrap(
                        Try.of(() -> JsonElement
                                .readFrom(schemaAsString)
                        ).get()),
                null)
                .generate()
                .toString()
        ).andThen(s -> log.debug("Сгенерировано сообщение: {}", s)
        ).get();
    }

    public boolean validateIt(JsonNode schema, JsonNode json) {
        return Try.of(() -> JsonSchemaFactory
                .byDefault()
                .getJsonSchema(schema)
                .validate(json, true)
                .isSuccess()
        ).get();
    }

}
