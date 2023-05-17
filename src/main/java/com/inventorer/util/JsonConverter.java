package com.inventorer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = true)
public class JsonConverter implements AttributeConverter<Map<String, Object>, String> {
  Logger log = LoggerFactory.getLogger(JsonConverter.class);
  private final static ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Map<String, Object> map) {
    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException ex) {
      log.error("Unable to convert map to string", ex);
      return null;
    }
  }

  @Override
  public Map<String, Object> convertToEntityAttribute(String jsonString) {
    try {
      return objectMapper.readValue(jsonString, HashMap.class);
    } catch (IOException ex) {
      log.error("Unable to parse json to map: " + jsonString, ex);
      return null;
    }
  }
}